package com.algaworks.works.controller;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.works.domain.Cliente;
import com.algaworks.works.domain.repository.ClienteRepository;
import com.algaworks.works.domain.service.CadastroClienteService;

@RestController
@RequestMapping("/clientes")
public class ClienteController {
	//@PersistenceContext
	//private EntityManager managerJpa;
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private CadastroClienteService cadastroClienteSevice;
	
	//@GetMapping("/clientes")
	@GetMapping()
	public List<Cliente> ListarCliente() {
		//*Utilizando EntityManger
		//return managerJpa.createQuery( "from Cliente", Cliente.class ).getResultList();
		
		//*Utilizando Spring Data JPA		
		return clienteRepository.findAll();
		//return clienteRepository.findByNome("Maria");
		//return clienteRepository.findByNomeContaining("J");
	}
	
	@GetMapping("/{clienteId}")
	public ResponseEntity<Cliente> buscar(@PathVariable Long clienteId) {
		Optional<Cliente> cliente = clienteRepository.findById(clienteId);
		
		if(cliente.isPresent()) {
			return ResponseEntity.ok(cliente.get());
		}
		
		return ResponseEntity.notFound().build();
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Cliente adicionar( @Valid @RequestBody Cliente cliente){
		//return clienteRepository.save(cliente);
		return cadastroClienteSevice.salvarCliente(cliente);
	}
	
	@PutMapping("/{clienteId}")
	public ResponseEntity<Cliente> atualizar(@PathVariable Long clienteId, 
			                                   @Valid  @RequestBody Cliente cliente){
		if(!clienteRepository.existsById(clienteId)) {
			return ResponseEntity.notFound().build();
		}
		
		cliente.setId(clienteId);
		//cliente = clienteRepository.save(cliente);
		cliente = cadastroClienteSevice.salvarCliente(cliente);
		
		return ResponseEntity.ok(cliente);	
	}
	
	@DeleteMapping("/{clienteId}")
	public 	ResponseEntity<Void> remover( @PathVariable Long clienteId){
		
		if(!clienteRepository.existsById(clienteId)) {
			return ResponseEntity.notFound().build();
		}
		//clienteRepository.deleteById(clienteId);
		cadastroClienteSevice.excluirCliente(clienteId);
		
		return ResponseEntity.noContent().build();
	}
}