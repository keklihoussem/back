package EMC.Web.emc.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;



@Entity
public class Cheque implements Serializable{
	@Id
	private Long numCheque;
	private Float montant;
	private String devise;
	private Date dateSaisie;
	private Date dateSortie;
	private Long numBordereau;
	private Date dateReception;
	private Date dateRejet;
	@Enumerated(EnumType.STRING)
	private StatutCheque statut;
	
	
	@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, 
			property  = "numBordereau", 
			scope     = Long.class)
	@JsonIgnoreProperties(value = {"listeCh","montant"}, allowSetters = true)
	@ManyToOne(cascade = {
            CascadeType.MERGE,
            CascadeType.REFRESH})
	@JoinColumn(name="bordereau_id")
	private Bordereau bordereau;
	
	@JsonBackReference(value="client-cheque")
	@ManyToOne(cascade = {
	    CascadeType.MERGE,
	    CascadeType.REFRESH})
	private Client clientCh;

	
	@JsonBackReference(value="banque-cheque")
	@ManyToOne(cascade = {
            CascadeType.MERGE,
            CascadeType.REFRESH})
	private Banque banque;
	
	@JsonBackReference(value="user-cheque")
	@ManyToOne(cascade = CascadeType.ALL)
	User User;

	@OneToOne
	private FileDB fileDb;
	
	public Cheque(Long numCheque, Float montant, String devise, Date dateSaisie, Date dateSortie, Long numBordereau,
			Date dateReception, Date dateRejet, StatutCheque statut, Bordereau bordereau, Client clientCh,
			Banque banque,User user) {
		super();
		this.numCheque = numCheque;
		this.montant = montant;
		this.devise = devise;
		this.dateSaisie = dateSaisie;
		this.dateSortie = dateSortie;
		this.numBordereau = numBordereau;
		this.dateReception = dateReception;
		this.dateRejet = dateRejet;
		this.statut = statut;
		this.bordereau = bordereau;
		this.clientCh = clientCh;
		this.banque = banque;
		User = user;
	}

	public Cheque() {
		super();
	}

	public Long getNumCheque() {
		return numCheque;
	}

	public void setNumCheque(Long numCheque) {
		this.numCheque = numCheque;
	}

	public Float getMontant() {
		return montant;
	}

	public void setMontant(Float montant) {
		this.montant = montant;
	}

	public String getDevise() {
		return devise;
	}

	public void setDevise(String devise) {
		this.devise = devise;
	}

	public Date getDateSaisie() {
		return dateSaisie;
	}

	public void setDateSaisie(Date dateSaisie) {
		this.dateSaisie = dateSaisie;
	}

	public Date getDateSortie() {
		return dateSortie;
	}

	public void setDateSortie(Date dateSortie) {
		this.dateSortie = dateSortie;
	}

	public Long getNumBordereau() {
		return numBordereau;
	}

	public void setNumBordereau(Long numBordereau) {
		this.numBordereau = numBordereau;
	}

	public Date getDateReception() {
		return dateReception;
	}

	public void setDateReception(Date dateReception) {
		this.dateReception = dateReception;
	}

	public Date getDateRejet() {
		return dateRejet;
	}

	public void setDateRejet(Date dateRejet) {
		this.dateRejet = dateRejet;
	}

	public StatutCheque getStatut() {
		return statut;
	}

	public void setStatut(StatutCheque statut) {
		this.statut = statut;
	}

	public Bordereau getBordereau() {
		return bordereau;
	}

	public void setBordereau(Bordereau bordereau) {
		this.bordereau = bordereau;
	}

	public Client getClientCh() {
		return clientCh;
	}

	public void setClientCh(Client clientCh) {
		this.clientCh = clientCh;
	}

	public Banque getBanque() {
		return banque;
	}

	public void setBanque(Banque banque) {
		this.banque = banque;
	}

	public User getUser() {
		return User;
	}

	public void setUser(User user) {
		User = user;
	}

	public FileDB getFileDb() {
		return fileDb;
	}

	public void setFileDb(FileDB fileDb) {
		this.fileDb = fileDb;
	}

	@Override
	public String toString() {
		return "Cheque [numCheque=" + numCheque + ", montant=" + montant + ", devise=" + devise + ", dateSaisie="
				+ dateSaisie + ", dateSortie=" + dateSortie + ", numBordereau=" + numBordereau + ", dateReception="
				+ dateReception + ", dateRejet=" + dateRejet + ", statut=" + statut + ", bordereau=" + bordereau
				+ ", clientCh=" + clientCh + ", banque=" + banque + ", User=" + User + "]";
	}

	
	
}
