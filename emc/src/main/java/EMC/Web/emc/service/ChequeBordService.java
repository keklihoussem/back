package EMC.Web.emc.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import EMC.Web.emc.entities.Banque;
import EMC.Web.emc.entities.Bordereau;
import EMC.Web.emc.entities.Cheque;
import EMC.Web.emc.entities.Client;
import EMC.Web.emc.entities.Compte;
import EMC.Web.emc.entities.FileDB;
import EMC.Web.emc.entities.StatutCheque;
import EMC.Web.emc.entities.User;
import EMC.Web.emc.exception.UserNotFoundException;
import EMC.Web.emc.repo.BanqueRepo;
import EMC.Web.emc.repo.BordereauRepository;
import EMC.Web.emc.repo.ChequeRepository;
import EMC.Web.emc.repo.ClientRepo;
import EMC.Web.emc.repo.CompteRepo;


@Service
@Transactional
public class ChequeBordService {
	 private final ChequeRepository repoC;
	 private final BordereauRepository repoB;
	 private final BanqueRepo repobanque;
	 private final ClientRepo repoclient;
	 private final CompteRepo repocompte;
	 
	 
	 @Autowired
	    private JavaMailSender emailSender;
	    @Autowired
	    public ChequeBordService(ChequeRepository Repo,BordereauRepository RepoB,BanqueRepo Repobanque,ClientRepo Repoclient,CompteRepo repocompte) {
	        this.repoC = Repo;
	        this.repoB=RepoB;
	        this.repobanque=Repobanque;
	        this.repoclient=Repoclient;
	        this.repocompte=repocompte;
	    }
	    public Integer LonguerCheque(Long numcheque) {
	    	String cheque = numcheque.toString();
	    	int len =cheque.length();
	    	if(len==7) {
	    		return 1;
	    	}
	    	else {
	    		return 2;
	    	}
	    }
	    
	    public Cheque créerCheque(Long numCheque,Float montant,String devise,Bordereau bor,Long numBordereau,FileDB image) {
	    	Cheque ch=new Cheque(numCheque,montant,devise,new Date(),null,numBordereau,null,null,null,bor,null,null,null);
	    	return ch;
	    }
	    public Bordereau créerBordereau(Long numBordereau,List<Cheque> chequeList ) {
	    	Bordereau bordereau=new Bordereau(numBordereau,new Date(),chequeList);
	    	return bordereau;
	    }
	    
	    public Bordereau RechercherBordereau(Long numBordereau ) {
	  	    	return repoB.findById(numBordereau)
	  	    			.orElseThrow(() -> new UserNotFoundException("bordereau introuvable"));
	    	
	    }
	    
	    public Cheque RechercherCheque(Long numCheque ) {
  	    	return repoC.findById(numCheque)
  	    			.orElseThrow(() -> new UserNotFoundException("cheque introuvable"));
    	
    }
	    
	    public Boolean ChequeExistant(Long numCheque) {
	    	if(repoC.findById(numCheque).isPresent()) {
	    		return true;
	    	}
	    	else {
	    		return false;
	    	}
	    }
	    
	    public List<Bordereau> afficherBordereau(){
	    	List<Bordereau> listeToday = new ArrayList<>();
	    	List<Bordereau> liste=repoB.findAll();
	    	if(liste.isEmpty()) {
	    		return null;
	    	}
	    	else{
	    		for(int i=0;i<liste.size();i++) {
	    			Date dateBordereau = liste.get(i).getDateBordereau();
	    			if(dateBordereau == null) {
	    				System.out.println("helloo");
	    				continue;
	    			}
	    			Calendar cal1 = Calendar.getInstance();
	    			cal1.setTime(dateBordereau);
	    			cal1.set(Calendar.HOUR_OF_DAY, 0);
	    			cal1.set(Calendar.MINUTE, 0);
	    			cal1.set(Calendar.SECOND, 0);
	    			cal1.set(Calendar.MILLISECOND, 0);

	    			Calendar cal2 = Calendar.getInstance();
	    			cal2.setTime(new Date());
	    			cal2.set(Calendar.HOUR_OF_DAY, 0);
	    			cal2.set(Calendar.MINUTE, 0);
	    			cal2.set(Calendar.SECOND, 0);
	    			cal2.set(Calendar.MILLISECOND, 0);

	    			if (cal1.getTime().equals(cal2.getTime())) {
	    				Bordereau b=liste.get(i);
	    				listeToday.add(b);
	    			}
	    			
	    			Bordereau bordereau=this.RechercherBordereau(liste.get(i).getNumBordereau());
	    			liste.get(i).setNumBordereau(bordereau.getNumBordereau());
	    			liste.get(i).setDateBordereau(bordereau.getDateBordereau());
	    			liste.get(i).setListeCh(bordereau.getListeCh());

	    		}
	    	}
	    	return listeToday;
	    }

	    
	    
	    public Integer NbrCheque() {
	    	Integer c=0;
			List<Cheque> listeCh=repoC.findAll();
			if(listeCh.isEmpty()) {
				return null;
			}
			else{
				for(int i=0;i<listeCh.size();i++) {
					Calendar cal1 = Calendar.getInstance();
					cal1.setTime(listeCh.get(i).getBordereau().getDateBordereau());
					cal1.set(Calendar.HOUR_OF_DAY, 0);
					cal1.set(Calendar.MINUTE, 0);
					cal1.set(Calendar.SECOND, 0);
					cal1.set(Calendar.MILLISECOND, 0);

					Calendar cal2 = Calendar.getInstance();
					cal2.setTime(new Date());
					cal2.set(Calendar.HOUR_OF_DAY, 0);
					cal2.set(Calendar.MINUTE, 0);
					cal2.set(Calendar.SECOND, 0);
					cal2.set(Calendar.MILLISECOND, 0);

					if (cal1.getTime().equals(cal2.getTime())) {
						c=c+1;
					}
					

				}

	    }
			return c;
	    }
	    
	    
	    
	    public List<Cheque> afficherToutCheques(){
	    	 List<Cheque> liste=repoC.findAll();
	    	 return liste;
	    }
	    
	    
	    
	    public List<Cheque> afficherCheques(){
	    	 List<Cheque> listeN=new ArrayList<>();
	    	 StatutCheque statut = null;
	    	 List<Cheque> liste=repoC.findAll();
				if(liste.isEmpty()) {
					return null;
				}
				else{
					for(int i=0;i<liste.size();i++) {
							if((liste.get(i).getStatut()==null)||(liste.get(i).getStatut()==statut.Reçu)) {
								listeN.add(liste.get(i));
							}
						}
					}
	    	 return listeN;
	    }
	    

	   
	    

	    

	    
//suivie
	    public List<Cheque> listeRecu(){
	    	List<Cheque> listeRecu = new ArrayList<Cheque>();
	    	StatutCheque statut = null;
	    	List<Cheque> liste=repoC.findAll();
			if(liste.isEmpty()) {
				return null;
			}
			else{
				for(int i=0;i<liste.size();i++) {
						if(liste.get(i).getStatut()==statut.Reçu) {
							listeRecu.add(liste.get(i));
						}
					}
				}
			
			return listeRecu;

	    }
	    
	    public List<Cheque> listeRejete(){
	    	List<Cheque> listeRejete = new ArrayList<Cheque>();
	    	StatutCheque statut = null;
	    	List<Cheque> liste=repoC.findAll();
			if(liste.isEmpty()) {
				return null;
			}
			else{
				for(int i=0;i<liste.size();i++) {
						if(liste.get(i).getStatut()==statut.rejeté) {
							listeRejete.add(liste.get(i));
						}
					}
				}
			
			return listeRejete;

	    }
	    
	    public void supprimerCheque(Cheque cheque){
	       Long numCh=cheque.getNumCheque();
	       repoC.deleteById(numCh);
	    }
	    
	    
	    public Compte trouverCompte(Long numCompte) {
	    	Compte compte=repocompte.findById(numCompte).orElse(null) ;
	    	return compte;
	    }
	    
	    public void enregistrerCompte(Compte compte){
		      repocompte.save(compte);
		    }
	    
	    public void enregistrerClient(Client client){
		      repoclient.save(client);
		    }
	  
	    //suivie traite
	    public List<Cheque> afficherChequesTraite(){
	    	 List<Cheque> listeN=new ArrayList<>();
	    	 List<Cheque> liste=repoC.findAll();
	    	 StatutCheque statut=null;
				if(liste.isEmpty()) {
					return null;
				}
				else{
					for(int i=0;i<liste.size();i++) {
							if((liste.get(i).getStatut()!=null)&&(liste.get(i).getStatut()!=statut.rejeté)
									&&(liste.get(i).getStatut()!=statut.Reçu)) {
								liste.get(i).setBanque(liste.get(i).getBanque());
								listeN.add(liste.get(i));
							}
						}
					}
	    	 return listeN;
	    }
	    
	    
	    
}