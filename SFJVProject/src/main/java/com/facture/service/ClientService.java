package com.facture.service;



import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.facture.bean.Client;
import com.facture.bean.User;
import com.facture.deo.ClientRepository;
import com.facture.deo.UserRepository;





@Service
@Transactional // das heisst dass der ganze service funktionieren muss oder gar nicht !
public class ClientService implements IClientService {
 
	@Autowired
	ClientRepository clientRepository;
	@Autowired
	UserRepository userRepository;
	
	
	@Override
	public Optional<Client> findClient(String user_id) {
	String client_id = clientRepository.findClient(user_id);
	if (client_id == null || client_id.isEmpty()) {
		 client_id = "";
	}
	return clientRepository.findById(client_id);
	}
 
	
    public Optional<Client> findClientByid(String client_id) {
	   return clientRepository.findById(client_id);
    }
	
	public void saveClient (String cin,String nom,String prenom,String adresse,String email,String username,Long telephone) {
		Optional<Client> client_1 = clientRepository.findById(cin);
		
		if(client_1.isPresent()) {
			int cmp=0;
			if(!nom.equals(client_1.get().getNom()) && !nom.isEmpty() && !nom.equals(" ")) {
				client_1.get().setNom(nom);
				++cmp;
			}
			if(!prenom.equals(client_1.get().getPrenom()) && !prenom.isEmpty() && !prenom.equals(" ")) {
				client_1.get().setPrenom(prenom);
				++cmp;
			}
			if(!adresse.equals(client_1.get().getAdresse()) && !adresse.isEmpty() && !adresse.equals(" ")) {
				client_1.get().setAdresse(adresse);
				++cmp;
			}
			if(!email.equals(client_1.get().getEmail()) && !email.isEmpty() && !email.equals(" ")) {
				client_1.get().setEmail(email);
				++cmp;
			}
			if(telephone != client_1.get().getTelephone() && telephone != 0 && telephone !=null) {
				client_1.get().setTelephone(telephone);
				++cmp;
			}if(cmp != 0) {
			clientRepository.save(client_1.get());
			}
			}else {
		Client client = new Client();
		User LoadedUser = userRepository.findUser(username);
		client.setId(cin);
		client.setPrenom(prenom);
		client.setAdresse(adresse);
		client.setNom(nom);
		client.setEmail(email);
		client.setTelephone(telephone);
		
		client.setUser(LoadedUser);
		clientRepository.save(client);
		}
		
	}
	
   public int signUpCheck(String user_id,String cin,String nom,String prenom,String adresse,String email, long telephone) {
        
		 Optional<Client> newClient = findClient(user_id);
		 if(newClient.isPresent() && newClient != null) {
				return 1;
			}
		 if (cin.equals(null) || cin.equals(" ") ) {
			 return -1;
		 }
		 if (nom.equals(null) || nom.equals(" ") || nom.isEmpty()) {
			 return -2;
		 }
		 if (email.equals(null) || email.equals(" ") || email.isEmpty()) {
			 return -3;
		 }
		 if (prenom.equals(null) || prenom.equals(" ") || prenom.isEmpty()) {
			 return -4;
		 }
		 
		 if (adresse == null || adresse.equals(" ") || adresse.isEmpty()) {
			 return -5;
		 }
		 if (telephone == 0 ) {
			 return -6;
		 }
		
		return 2;
	}

   public void removeById(Client client) {
	   if(client != null) {
	   clientRepository.delete(client);
	   }
	   }
   
   public List<Client> findAll(){
	   return  clientRepository.findAll();
   }

}
