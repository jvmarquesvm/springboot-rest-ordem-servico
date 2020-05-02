package com.algaworks.works.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.algaworks.works.domain.Cliente;
import com.algaworks.works.domain.repository.ClienteRepository;
import com.algaworks.works.exception.NegocioException;

@Service
public class CadastroClienteService {
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	public Cliente salvarCliente(Cliente cliente) {
		
		Cliente clienteExistente = clienteRepository.findByEmail(cliente.getEmail());
		
		if(clienteExistente != null && !clienteExistente.equals(cliente) ) {
			throw new NegocioException("Já existe um cliente cadastrado com este e-mail");
		}
		
		return clienteRepository.save(cliente);
	}
	
	public void excluirCliente(Long clientId) {
		clienteRepository.deleteById(clientId);
	}
}
