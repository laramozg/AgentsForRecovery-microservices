package org.example.sportsorder.controllers.mutilation;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.sportsorder.controllers.mutilation.dto.MutilationDto;
import org.example.sportsorder.controllers.mutilation.dto.MutilationRequest;
import org.example.sportsorder.controllers.mutilation.dto.MutilationResponse;
import org.example.sportsorder.mappers.MutilationMapper;
import org.example.sportsorder.models.Mutilation;
import org.example.sportsorder.services.MutilationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/mutilations")
public class MutilationController {
    public static final int DEFAULT_PAGE_SIZE = 50;
    private final MutilationService mutilationService;
    private final MutilationMapper mutilationMapper;
    private static final Logger logger = LoggerFactory.getLogger(MutilationController.class);


    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyAuthority('SUPERVISOR', 'CUSTOMER')")
    public MutilationResponse create(
            @Valid @RequestBody MutilationRequest mutilationRequest) {
        logger.trace("Create mutilation request: {}", mutilationRequest);
        UUID mutilation = mutilationService.createMutilation(mutilationMapper.convertToEntity(mutilationRequest));
        return new MutilationResponse(mutilation);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Page<MutilationDto> findAllMutilations(
            @Schema(hidden = true) @PageableDefault(size = DEFAULT_PAGE_SIZE) Pageable pageable
    ) {
        logger.trace("Find all mutilations");
        return mutilationService.findAllMutilations(pageable)
                .map(mutilationMapper::convertToDto);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public MutilationDto findMutilationById(@PathVariable UUID id) {
        logger.trace("Find mutilation by id: {}", id);
        return mutilationMapper.convertToDto(mutilationService.find(id));
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyAuthority('SUPERVISOR')")
    public MutilationDto update(
            @PathVariable UUID id,
            @RequestBody MutilationRequest mutilationRequest) {
        logger.trace("Update mutilation request: {}", mutilationRequest);
        Mutilation mutilation = mutilationService.updateMutilation(id, mutilationMapper.convertToEntity(mutilationRequest));
        return mutilationMapper.convertToDto(mutilation);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAnyAuthority('SUPERVISOR')")
    public void delete(@PathVariable UUID id) {
        logger.trace("Delete mutilation by id: {}", id);
        mutilationService.deleteMutilation(id);
    }
}
