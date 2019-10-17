package com.kolevic.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.kolevic.constant.ViewConstant;
import com.kolevic.model.ContactModel;
import com.kolevic.service.ContactService;

@Controller
@RequestMapping("/contacts")
public class ContactController {
	
	private static final Log LOG = LogFactory.getLog(ContactController.class);
	
	@Autowired
	@Qualifier("contactServiceImpl")
	private ContactService contactService;

	@GetMapping("/cancel")
	public String cancel() {
		return "redirect:/contacts/showcontacts";
	}
	
	
	@GetMapping("/contactform")
	private String redirectContactForm(@RequestParam(name="id") int id, Model model) {
		if (0 != id) {
			model.addAttribute("contact", contactService.findContactById(id));
		} else {
			model.addAttribute("contact", new ContactModel());
		}
		return ViewConstant.CONTACT_FORM;
	}
	
	
	@PostMapping("/addcontact")
	public String addContact(@ModelAttribute(name="contactModel") ContactModel contactModel,
			Model model) {
		LOG.info("METHOD: addContact()--PARAMS:"+ contactModel.toString());
		
		if(null != contactService.addContact(contactModel)) {
			model.addAttribute("result", 1);
		}else {
			model.addAttribute("result", 0);
		}				
	return "redirect:/contacts/showcontacts";
	}
	
	@GetMapping("/showcontacts")
	public ModelAndView showContacts() {
		ModelAndView mav = new ModelAndView(ViewConstant.CONTACTS);
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		mav.addObject("username", user.getUsername());
		mav.addObject("contacts", contactService.listAllContacts());
		ContactModel con = new ContactModel();
		LOG.info(con.toString());
		mav.addObject("contactfind", con);
		return mav;
	}
	
	@GetMapping("/removecontact")
	public ModelAndView removeContact(@RequestParam(name="id", required=true) int id) {
		contactService.removeContact(id);
		return showContacts();
	}
	
	@PostMapping("/find")
	public ModelAndView findContact(@ModelAttribute(name="contactfind") ContactModel contactmodel) {
		ModelAndView mav = new ModelAndView(ViewConstant.CONTACTS);
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		mav.addObject("username", user.getUsername());
		ContactModel contact = contactService.findContactByIdModel(contactmodel.getId());
		mav.addObject("contactfind", contact);
		mav.addObject("contacts", contactService.listAllContacts());
		return mav;
	}
	
}
	


