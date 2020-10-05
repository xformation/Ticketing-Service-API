package com.syn.tkt.service.impl;

import com.syn.tkt.service.TicketAuditReportService;
import com.syn.tkt.domain.TicketAuditReport;
import com.syn.tkt.repository.TicketAuditReportRepository;
import com.syn.tkt.service.dto.TicketAuditReportDTO;
import com.syn.tkt.service.mapper.TicketAuditReportMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link TicketAuditReport}.
 */
@Service
@Transactional
public class TicketAuditReportServiceImpl implements TicketAuditReportService {

    private final Logger log = LoggerFactory.getLogger(TicketAuditReportServiceImpl.class);

    private final TicketAuditReportRepository ticketAuditReportRepository;

    private final TicketAuditReportMapper ticketAuditReportMapper;

    public TicketAuditReportServiceImpl(TicketAuditReportRepository ticketAuditReportRepository, TicketAuditReportMapper ticketAuditReportMapper) {
        this.ticketAuditReportRepository = ticketAuditReportRepository;
        this.ticketAuditReportMapper = ticketAuditReportMapper;
    }

    /**
     * Save a ticketAuditReport.
     *
     * @param ticketAuditReportDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public TicketAuditReportDTO save(TicketAuditReportDTO ticketAuditReportDTO) {
        log.debug("Request to save TicketAuditReport : {}", ticketAuditReportDTO);
        TicketAuditReport ticketAuditReport = ticketAuditReportMapper.toEntity(ticketAuditReportDTO);
        ticketAuditReport = ticketAuditReportRepository.save(ticketAuditReport);
        return ticketAuditReportMapper.toDto(ticketAuditReport);
    }

    /**
     * Get all the ticketAuditReports.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<TicketAuditReportDTO> findAll() {
        log.debug("Request to get all TicketAuditReports");
        return ticketAuditReportRepository.findAll().stream()
            .map(ticketAuditReportMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one ticketAuditReport by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<TicketAuditReportDTO> findOne(Long id) {
        log.debug("Request to get TicketAuditReport : {}", id);
        return ticketAuditReportRepository.findById(id)
            .map(ticketAuditReportMapper::toDto);
    }

    /**
     * Delete the ticketAuditReport by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete TicketAuditReport : {}", id);
        ticketAuditReportRepository.deleteById(id);
    }
}
