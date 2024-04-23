package com.synectiks.tkt.service.impl;

import com.synectiks.tkt.service.EmailTicketAssociationService;
import com.synectiks.tkt.domain.EmailTicketAssociation;
import com.synectiks.tkt.repository.EmailTicketAssociationRepository;
import com.synectiks.tkt.service.dto.EmailTicketAssociationDTO;
import com.synectiks.tkt.service.mapper.EmailTicketAssociationMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link EmailTicketAssociation}.
 */
@Service
@Transactional
public class EmailTicketAssociationServiceImpl implements EmailTicketAssociationService {

    private final Logger log = LoggerFactory.getLogger(EmailTicketAssociationServiceImpl.class);

    private final EmailTicketAssociationRepository emailTicketAssociationRepository;

    private final EmailTicketAssociationMapper emailTicketAssociationMapper;

    public EmailTicketAssociationServiceImpl(EmailTicketAssociationRepository emailTicketAssociationRepository, EmailTicketAssociationMapper emailTicketAssociationMapper) {
        this.emailTicketAssociationRepository = emailTicketAssociationRepository;
        this.emailTicketAssociationMapper = emailTicketAssociationMapper;
    }

    @Override
    public EmailTicketAssociationDTO save(EmailTicketAssociationDTO emailTicketAssociationDTO) {
        log.debug("Request to save EmailTicketAssociation : {}", emailTicketAssociationDTO);
        EmailTicketAssociation emailTicketAssociation = emailTicketAssociationMapper.toEntity(emailTicketAssociationDTO);
        emailTicketAssociation = emailTicketAssociationRepository.save(emailTicketAssociation);
        return emailTicketAssociationMapper.toDto(emailTicketAssociation);
    }

    @Override
    @Transactional(readOnly = true)
    public List<EmailTicketAssociationDTO> findAll() {
        log.debug("Request to get all EmailTicketAssociations");
        return emailTicketAssociationRepository.findAll().stream()
            .map(emailTicketAssociationMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<EmailTicketAssociationDTO> findOne(Long id) {
        log.debug("Request to get EmailTicketAssociation : {}", id);
        return emailTicketAssociationRepository.findById(id)
            .map(emailTicketAssociationMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete EmailTicketAssociation : {}", id);
        emailTicketAssociationRepository.deleteById(id);
    }
}
