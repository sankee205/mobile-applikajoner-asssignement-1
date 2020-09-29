/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.mavenassignement.resources;

import com.mycompany.mavenassignement.Mail;
import com.mycompany.mavenassignement.Item;
import com.mycompany.mavenassignement.Photo;
import com.mycompany.mavenassignement.authentication.AuthenticationService;
import com.mycompany.mavenassignement.authentication.Group;
import com.mycompany.mavenassignement.authentication.User;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.CacheControl;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.StreamingOutput;
import net.coobird.thumbnailator.Thumbnails;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.glassfish.jersey.media.multipart.ContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataBodyPart;
import org.glassfish.jersey.media.multipart.FormDataMultiPart;
import org.glassfish.jersey.media.multipart.FormDataParam;


/**
 *
 * @author sanderkeedklang
 */
@Produces(MediaType.APPLICATION_JSON)
@Path("fant")
@Stateless
public class FantServiceResource {
   
    @Inject
    AuthenticationService authenticationService;

    @Context
    SecurityContext securityContext;

    //@Inject
    //JsonWebToken principal;

    @PersistenceContext
    EntityManager entityManager;

    @Inject
    @ConfigProperty(name = "photo.storage.path", defaultValue = "fant_images")
    String photoPath;

    @GET
    @Path("items")
    //@RolesAllowed({Group.USER})
    @Produces(MediaType.APPLICATION_JSON)
    public List<Item> getItems() {
        return entityManager.createNativeQuery("SELECT * FROM Item", Item.class).getResultList();
    }

    @PUT
    @Path("purchase")
    @RolesAllowed({Group.USER})
    public Response purchase(@QueryParam("itemid") Long itemid) {

        Item item = entityManager.find(Item.class, itemid);

        if (item != null){
            if (item.getItemBuyer() == null){
                User buyer = this.getCurrentUser();
                item.setItemBuyer(buyer);
                Mail.sendEmail(item.getItemOwner().getEmail(), "your thing has been sold", "somthing somthing bip bop" );

                return Response.ok().build();
            }

        }
        return Response.notModified().build();

    }

    @DELETE
    @Path("delete")
    @RolesAllowed({Group.USER})
    public Response delete(@QueryParam("itemid") Long itemid) {
        Item item = entityManager.find(Item.class, itemid);
        if (item != null){
            User user = this.getCurrentUser();
            if (item.getItemBuyer().getUsername().equals(user.getUsername())){
                entityManager.remove(item);
                return Response.ok().build();
            }

        }
        return Response.notModified().build();
    }

    @POST
    @Path("add-item")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @RolesAllowed({Group.USER})
    public Response addItem(
            @FormDataParam("title")String title,
            @FormDataParam("desc")String description,
            @FormDataParam("price")BigDecimal price,
            FormDataMultiPart photos
    ){
        User user = this.getCurrentUser();
        Item newItem = new Item();

        //System.out.printf("Pname<%s>", principal.getName());
        //System.out.printf("Psub<%s>", principal.getSubject());
        //System.out.printf("SC name<%s>", securityContext.getUserPrincipal().getName());

        newItem.setItemOwner(user);
        newItem.setTitle(title);
        newItem.setDescription(description);
        newItem.setPrice(price);
        ArrayList<Photo> p = new ArrayList<>();


        try{


            List<FormDataBodyPart> images = photos.getFields("image");


            if(images != null) {


                for (FormDataBodyPart part : images) {
                    InputStream is = part.getEntityAs(InputStream.class);
                    ContentDisposition meta = part.getContentDisposition();

                    String pid = UUID.randomUUID().toString();
                    Files.copy(is, Paths.get(photoPath, pid));

                    Photo photo = new Photo();
                    photo.setId(pid);
                    photo.setName(meta.getFileName());
                    photo.setFilesize(meta.getSize());
                    photo.setMimeType(meta.getType());



                    p.add(photo);




                    entityManager.persist(photo);
                }

            }

        } catch (Exception e){
            e.printStackTrace();
        }
        newItem.setItemImages(p);
        entityManager.persist(newItem);

        return Response.ok().build();

    }

    @GET
    @Path("image/{name}")
    @Produces("image/jpeg")
    public Response getPhoto(@PathParam("name") String name, @QueryParam("width") int width) {
        if(entityManager.find(Photo.class, name) != null) {
            StreamingOutput result = (OutputStream os) -> {
                java.nio.file.Path image = Paths.get(photoPath,name);
                if(width == 0) {
                    Files.copy(image, os);
                    os.flush();
                } else {
                    Thumbnails.of(image.toFile())
                            .size(width, width)
                            .outputFormat("jpeg")
                            .toOutputStream(os);
                }
            };

            // Ask the browser to cache the image for 24 hours
            CacheControl cc = new CacheControl();
            cc.setMaxAge(86400);
            cc.setPrivate(true);

            return Response.ok(result).cacheControl(cc).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

    }


    private User getCurrentUser(){
        //System.out.printf("Pname low <%s>", principal.getName());
        return entityManager.find(User.class, securityContext.getUserPrincipal().getName());
    }
}