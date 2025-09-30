package finalprojectprogramming.project.controllers;

import finalprojectprogramming.project.dtos.ReservationDTO;
import finalprojectprogramming.project.services.reservation.ReservationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/reservations")
@Validated
@Tag(name = "Reservations", description = "Operations related to reservations management")
public class ReservationController {

    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @PostMapping
    @Operation(summary = "Create a new reservation")
    public ResponseEntity<ReservationDTO> createReservation(@Valid @RequestBody ReservationDTO reservationDTO) {
        ReservationDTO created = reservationService.create(reservationDTO);
        return ResponseEntity.created(URI.create("/api/reservations/" + created.getId())).body(created);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing reservation")
    public ResponseEntity<ReservationDTO> updateReservation(@PathVariable Long id,
            @Valid @RequestBody ReservationDTO reservationDTO) {
        return ResponseEntity.ok(reservationService.update(id, reservationDTO));
    }

    @GetMapping
    @Operation(summary = "Retrieve all reservations")
    public ResponseEntity<List<ReservationDTO>> getAllReservations() {
        return ResponseEntity.ok(reservationService.findAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Retrieve a reservation by id")
    public ResponseEntity<ReservationDTO> getReservationById(@PathVariable Long id) {
        return ResponseEntity.ok(reservationService.findById(id));
    }

    @GetMapping("/user/{userId}")
    @Operation(summary = "Retrieve reservations by user")
    public ResponseEntity<List<ReservationDTO>> getReservationsByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(reservationService.findByUser(userId));
    }

    @GetMapping("/space/{spaceId}")
    @Operation(summary = "Retrieve reservations by space")
    public ResponseEntity<List<ReservationDTO>> getReservationsBySpace(@PathVariable Long spaceId) {
        return ResponseEntity.ok(reservationService.findBySpace(spaceId));
    }

    @PostMapping("/{id}/cancel")
    @Operation(summary = "Cancel a reservation")
    public ResponseEntity<ReservationDTO> cancelReservation(@PathVariable Long id,
            @RequestBody(required = false) CancellationRequest request) {
        String reason = request != null ? request.reason() : null;
        return ResponseEntity.ok(reservationService.cancel(id, reason));
    }

    @PostMapping("/{id}/approve")
    @Operation(summary = "Approve a reservation")
    public ResponseEntity<ReservationDTO> approveReservation(@PathVariable Long id,
            @Valid @RequestBody ApprovalRequest request) {
        return ResponseEntity.ok(reservationService.approve(id, request.approverUserId()));
    }

    @PostMapping("/{id}/check-in")
    @Operation(summary = "Register reservation check-in")
    public ResponseEntity<ReservationDTO> markCheckIn(@PathVariable Long id) {
        return ResponseEntity.ok(reservationService.markCheckIn(id));
    }

    @PostMapping("/{id}/no-show")
    @Operation(summary = "Mark reservation as no-show")
    public ResponseEntity<ReservationDTO> markNoShow(@PathVariable Long id) {
        return ResponseEntity.ok(reservationService.markNoShow(id));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Soft delete a reservation")
    public ResponseEntity<Void> deleteReservation(@PathVariable Long id) {
        reservationService.delete(id);
        return ResponseEntity.noContent().build();
    }

    public record CancellationRequest(String reason) {
    }

    public record ApprovalRequest(@NotNull Long approverUserId) {
    }
}