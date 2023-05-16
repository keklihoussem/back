package EMC.Web.emc.controlleur;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import EMC.Web.emc.entities.Client;
import EMC.Web.emc.entities.Compte;
import EMC.Web.emc.entities.Role;
import EMC.Web.emc.entities.User;
import EMC.Web.emc.repo.ClientRepo;
import EMC.Web.emc.service.ChequeBordService;
import EMC.Web.emc.service.UserService;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class user_controller {
	@Autowired
	private UserService us;
	@Autowired
	private ClientRepo clientR;
	
	@Autowired
	private ChequeBordService service;
	@GetMapping("/login/{matricule}/{pwd}")
	public ResponseEntity<User> login(@PathVariable("matricule")Long matricule,@PathVariable("pwd")String pwd){
		Integer t=us.UserExists(matricule, pwd);
		if(t==0) {
			User user=us.FindUser(matricule, pwd);
			return new ResponseEntity<>(user, HttpStatus.OK);
		}
		else if(t==1) {
			User user=us.FindUser(matricule, pwd);
			return new ResponseEntity<>(user, HttpStatus.OK);
		}
		else {
			User u=us.FindMatricule(matricule);
			return new ResponseEntity<>(u, HttpStatus.OK);
		}
			
	}
	
	@GetMapping("/iden/{matricule}/{pwd}")
	public ResponseEntity<Integer> identifier(@PathVariable("matricule")Long matricule,@PathVariable("pwd")String pwd){
		Integer t=us.UserExists(matricule, pwd);
		return new ResponseEntity<>(t, HttpStatus.OK);
		
	}
	
	//espace client
	@GetMapping("/retournerClient/{matricule}")
	public ResponseEntity<Client> espaceClient(@PathVariable("matricule")Long matricule){
		User user=us.FindMatricule(matricule);
		Client client=user.getClient();
		return new ResponseEntity<>(client,HttpStatus.OK);
		
	}
	
	@GetMapping("/retournerCompte/{numClient}")
	public ResponseEntity<Compte> espaceCompte(@PathVariable("numClient")Long numClient){
		Client client=clientR.findById(numClient).orElse(null) ;
		Compte compte=client.getCompte();
		return new ResponseEntity<>(compte,HttpStatus.OK);
		
	}
	
	//admin-user
	
	@GetMapping("/afficherUsers")
	public ResponseEntity<List<User>> AffichertoutUsers(){
		List<User> liste=us.afficherUsers();
		return new ResponseEntity<>(liste, HttpStatus.OK);
		
	}
	
	@DeleteMapping("/supprimerUser")
    public ResponseEntity<?> supprimerUser(@RequestBody User user) {
       us.supprimerUser(user);
       return new ResponseEntity<>(HttpStatus.OK);
    }
	
	@GetMapping("/creeUser/{matricule}/{pwd}/{nom}/{prenom}/{email}/{numtel}/{role}")
	public ResponseEntity<User> creeUser(@PathVariable("matricule")Long matricule,@PathVariable("pwd")String pwd,@PathVariable("nom")String nom,@PathVariable("prenom")String prenom,@PathVariable("email")String email,@PathVariable("numtel")Long numtel,@PathVariable("role")String role){
		Role r = Role.fromString(role);
		User user=new User(matricule,pwd,nom,prenom,email,numtel,r,null,null,null);
		return new ResponseEntity<>(user, HttpStatus.OK);
		
	}
	
	@PostMapping("/enregistrerUser")
	public ResponseEntity<User> enregistrerUser(@RequestBody User user){
		us.enregistrer(user);
		return new ResponseEntity<>(user, HttpStatus.OK);
		
	}
	
	@PostMapping("/UserClient")
	public ResponseEntity<Client> UserClient(@RequestBody User user){
		Client client=new Client();
		client.setUser(user);
		client.setMailClient(user.getEmail());
		client.setNomClient(user.getNom());
		client.setNumeroTelephone(user.getTelephone());
		client.setPrenomClient(user.getPrenom());
		clientR.save(client);
		user.setClient(client);
		us.enregistrer(user);
		return new ResponseEntity<>(client, HttpStatus.OK);
		
	}
	
	@GetMapping("/compte/{numCompte}/{rib}")
	 public ResponseEntity<Compte> creeCompte(@PathVariable("numCompte")Long numCompte,@PathVariable("rib")Long rib) {
		
			Compte compte=new Compte(numCompte,rib,null);
			return new ResponseEntity<>(compte,HttpStatus.OK);
		
		
	}
	
	@PostMapping("/enregistrerCompte")
	public ResponseEntity<Compte> enregistrerUser(@RequestBody Compte compte){
		service.enregistrerCompte(compte);
		return new ResponseEntity<>(compte, HttpStatus.OK);
		
	}
	
	@PutMapping("/relierCompteClient/{matricule}")
	public ResponseEntity<Compte> enregistrerCompte(@PathVariable("matricule")Long matricule,@RequestBody Compte compte){
		User user=us.FindMatricule(matricule);
		Client client=user.getClient();
		client.setCompte(compte);
		compte.setClient(client);
		service.enregistrerCompte(compte);
		service.enregistrerClient(client);
		return new ResponseEntity<>(compte, HttpStatus.OK);
		
	}
	
	
	
	
	
	
	
	

}
