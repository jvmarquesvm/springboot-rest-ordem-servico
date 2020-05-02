package com.algaworks.works.domain.service;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.algaworks.works.domain.Cliente;
import com.algaworks.works.domain.OrdemServico;
import com.algaworks.works.domain.StatusOrdemServico;
import com.algaworks.works.domain.repository.ClienteRepository;
import com.algaworks.works.domain.repository.OrdemServicoRepository;
import com.algaworks.works.exception.NegocioException;

@Service
public class GestaoOrdemServicoService {
	@Autowired
	OrdemServicoRepository ordemServicoRepository;
	@Autowired
	private ClienteRepository clienteRepository;
	
	public OrdemServico criar(OrdemServico ordemServico) {
		Cliente cliente = clienteRepository.findById(ordemServico.getCliente().getId())
				                                    .orElseThrow( () -> new NegocioException("Cliente não encontrado"));
		ordemServico.setCliente(cliente);
		ordemServico.setStatus(StatusOrdemServico.ABERTA);
		ordemServico.setDataAbertura(OffsetDateTime.now());
		return ordemServicoRepository.save(ordemServico);
	}
}
