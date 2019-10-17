package com.kolevic.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.kolevic.Component.ContactConverter;
import com.kolevic.model.ContactModel;
import com.kolevic.service.ContactService;

@org.springframework.web.bind.annotation.RestController
@RequestMapping("/rest")
public class RestController {

	@Autowired
	@Qualifier("contactServiceImpl")
	private ContactService contactService;
	
	@Autowired
	@Qualifier("contactConverter")
	private ContactConverter contactConverter;
	
	@GetMapping("/checkrest")
	public ResponseEntity<ContactModel> checkRest(){
		ContactModel cm = new ContactModel(2, "Juan", "Castilla", "98633245", "Lima");
		return new ResponseEntity<ContactModel>(cm, HttpStatus.OK);
	}
	
	@GetMapping("/listcontact")
	public ResponseEntity<List<ContactModel>> listarContactos() {
		List<ContactModel> listcon = contactService.listAllContacts();
		return new ResponseEntity<List<ContactModel>>(listcon, HttpStatus.OK);
	}
	
	@PostMapping("/addcontact")
	public ResponseEntity<ContactModel> agregarContacto(@RequestBody ContactModel contactModel){
		ContactModel contac = new ContactModel();
		contac = contactService.addContact(contactModel);
		return new ResponseEntity<ContactModel>(contac, HttpStatus.OK);
	}
	
	@DeleteMapping("/removecontact")
	public void  eliminarContacto(@PathVariable("id") int id,
			@PathVariable("firstname") String firstname){
		contactService.removeContact(id);
		
	}
	
}
