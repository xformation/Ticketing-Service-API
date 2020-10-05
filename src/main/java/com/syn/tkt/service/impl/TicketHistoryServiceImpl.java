package com.syn.tkt.service.impl;

import com.syn.tkt.service.TicketHistoryService;
import com.syn.tkt.domain.TicketHistory;
import com.syn.tkt.repository.TicketHistoryRepository;
import com.syn.tkt.service.dto.TicketHistoryDTO;
import com.syn.tkt.service.mapper.TicketHistoryMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link TicketHistory}.
 */
@Service
@Transactional
public class TicketHistoryServiceImpl implements TicketHistoryService {

    private final Logger log = LoggerFactory.getLogger(TicketHistoryServiceImpl.class);

    private final TicketHistoryRepository ticketHistoryRepository;

    private final TicketHistoryMapper ticketHistoryMapper;

    public TicketHistoryServiceImpl(TicketHistoryRepository ticketHistoryRepository, TicketHistoryMapper ticketHistoryMapper) {
        this.ticketHistoryRepository = ticketHistoryRepository;
        this.ticketHistoryMapper = ticketHistoryMapper;
    }

    /**
     * Save a ticketHistory.
     *
     * @param ticketHistoryDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public TicketHistoryDTO save(TicketHistoryDTO ticketHistoryDTO) {
        log.debug("Request to save TicketHistory : {}", ticketHistoryDTO);
        TicketHistory ticketHistory = ticketHistoryMapper.toEntity(ticketHistoryDTO);
        ticketHistory = ticketHistoryRepository.save(ticketHistory);
        return ticketHistoryMapper.toDto(ticketHistory);
    }

    /**
     * Get all the ticketHistories.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<TicketHistoryDTO> findAll() {
        log.debug("Request to get all TicketHistories");
        return ticketHistoryRepository.findAll().stream()
            .map(ticketHistoryMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one ticketHistory by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<TicketHistoryDTO> findOne(Long id) {
        log.debug("Request to get TicketHistory : {}", id);
        return ticketHistoryRepository.findById(id)
            .map(ticketHistoryMapper::toDto);
    }

    /**
     * Delete the ticketHistory by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete TicketHistory : {}", id);
        ticketHistoryRepository.deleteById(id);
    }
}
