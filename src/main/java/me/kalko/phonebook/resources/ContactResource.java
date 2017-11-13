package me.kalko.phonebook.resources;

import com.fasterxml.jackson.annotation.JsonFilter;
import io.dropwizard.auth.Auth;
import me.kalko.phonebook.domain.Contact;
import me.kalko.phonebook.domain.IdWrapper;
import me.kalko.phonebook.domain.Phone;
import me.kalko.phonebook.domain.User;
import me.kalko.phonebook.domain.dao.ContactService;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@Path("/contacts")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ContactResource {

    private final ContactService contactService;

    public ContactResource(ContactService contactService) {
        this.contactService = contactService;
    }

    @GET
    public Response getContacts(@Auth User user) {
        return Response.ok()
                .entity(contactService.getContacts(user.getId()))
                .build();
    }

    @POST
    public Response createContact(@Auth User user, Contact contact) {
        Contact createdContact = contactService.createContact(user.getId(), contact);
        int contactId = createdContact.getId();
        return Response.created(URI.create("/contacts/" + contactId)).entity(new IdWrapper(contactId)).build();
    }

    @GET
    @Path("/{contactId}")
    public Response getContact(@Auth User user, @PathParam("contactId") int contactId) {
        Contact contact = contactService.getContact(contactId);
        if (contact == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("{}").build();
        }
        if (contact.getUserId() != user.getId()) {
            return Response.status(Response.Status.UNAUTHORIZED).entity("{}").build();
        }
        return Response.ok().entity(contact).build();
    }

    @POST
    @Path("/{contactId}")
    public Response updateContact(@Auth User user, @PathParam("contactId") int contactId, Contact updatedContact) {
        Contact contact = contactService.getContact(contactId);
        if (contact == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("{}").build();
        }
        if (contact.getUserId() != user.getId()) {
            return Response.status(Response.Status.UNAUTHORIZED).entity("{}").build();
        }
        contact = contactService.updateContact(contactId, updatedContact);
        return Response.ok().entity(new IdWrapper(contactId)).build();
    }

    @DELETE
    @Path("/{contactId}")
    public Response deleteContact(@Auth User user, @PathParam("contactId") int contactId) {
        Contact contact = contactService.getContact(contactId);
        if (contact == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("{}").build();
        }
        if (contact.getUserId() != user.getId()) {
            return Response.status(Response.Status.UNAUTHORIZED).entity("{}").build();
        }
        contactService.deleteContact(contactId);
        return Response.ok().build();
    }

    @POST
    @Path("/{contactId}/entries")
    public Response addPhone(@Auth User user, @PathParam("contactId") int contactId, Phone phone) {
        Contact contact = contactService.getContact(contactId);
        if (contact == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("{}").build();
        }
        if (contact.getUserId() != user.getId()) {
            return Response.status(Response.Status.UNAUTHORIZED).entity("{}").build();
        }
        contact.addPhone(phone.getPhone());
        contact = contactService.updateContact(contactId, contact);
        return Response.ok().entity(new IdWrapper(contactId)).build();
    }
}
