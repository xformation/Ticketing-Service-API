package com.syn.tkt.service.impl;

import com.syn.tkt.service.TicketAuditHistoryService;
import com.syn.tkt.domain.TicketAuditHistory;
import com.syn.tkt.repository.TicketAuditHistoryRepository;
import com.syn.tkt.service.dto.TicketAuditHistoryDTO;
import com.syn.tkt.service.mapper.TicketAuditHistoryMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link TicketAuditHistory}.
 */
@Service
@Transactional
public class TicketAuditHistoryServiceImpl implements TicketAuditHistoryService {

    private final Logger log = LoggerFactory.getLogger(TicketAuditHistoryServiceImpl.class);

    private final TicketAuditHistoryRepository ticketAuditHistoryRepository;

    private final TicketAuditHistoryMapper ticketAuditHistoryMapper;

    public TicketAuditHistoryServiceImpl(TicketAuditHistoryRepository ticketAuditHistoryRepository, TicketAuditHistoryMapper ticketAuditHistoryMapper) {
        this.ticketAuditHistoryRepository = ticketAuditHistoryRepository;
        this.ticketAuditHistoryMapper = ticketAuditHistoryMapper;
    }

    /**
     * Save a ticketAuditHistory.
     *
     * @param ticketAuditHistoryDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public TicketAuditHistoryDTO save(TicketAuditHistoryDTO ticketAuditHistoryDTO) {
        log.debug("Request to save TicketAuditHistory : {}", ticketAuditHistoryDTO);
        TicketAuditHistory ticketAuditHistory = ticketAuditHistoryMapper.toEntity(ticketAuditHistoryDTO);
        ticketAuditHistory = ticketAuditHistoryRepository.save(ticketAuditHistory);
        return ticketAuditHistoryMapper.toDto(ticketAuditHistory);
    }

    /**
     * Get all the ticketAuditHistories.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<TicketAuditHistoryDTO> findAll() {
        log.debug("Request to get all TicketAuditHistories");
        return ticketAuditHistoryRepository.findAll().stream()
            .map(ticketAuditHistoryMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one ticketAuditHistory by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<TicketAuditHistoryDTO> findOne(Long id) {
        log.debug("Request to get TicketAuditHistory : {}", id);
        return ticketAuditHistoryRepository.findById(id)
            .map(ticketAuditHistoryMapper::toDto);
    }

    /**
     * Delete the ticketAuditHistory by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete TicketAuditHistory : {}", id);
        ticketAuditHistoryRepository.deleteById(id);
    }
}
