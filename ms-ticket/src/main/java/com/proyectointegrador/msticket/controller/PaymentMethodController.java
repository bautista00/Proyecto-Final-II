package com.proyectointegrador.msticket.controller;

import com.proyectointegrador.msticket.domain.PaymentMethod;
import com.proyectointegrador.msticket.service.interfaces.IPaymentMethodService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/paymentMethod")
@RequiredArgsConstructor
@Tag(name = "Payment Method Controller", description = "Operaciones relacionadas a Payment Method")
public class PaymentMethodController {

    private final IPaymentMethodService paymentMethodService;

    @Operation(summary = "Crear un PaymentMethod(método de pago)", description = "Crea un nuevo PaymentMethod(método de pago)",
        requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                content = @Content(
                        mediaType = "application/json",
                        examples = @ExampleObject(
                                value = """
                                        {
                                            "category": "Credit Card",
                                            "detail": "Visa"
                                        }
                                        """
                        )
                )
        )
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Método de pago creado"),
    })
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/private/create")
    public ResponseEntity<PaymentMethod> createPaymentMethod(@RequestBody PaymentMethod paymentMethod) {
        PaymentMethod paymentMethodCreated = paymentMethodService.createPayment(paymentMethod);
        return ResponseEntity.ok(paymentMethodCreated);
    }

    @Operation(summary = "Actualizar un PaymentMethod(método de pago)", description = "Actualiza un PaymenyMethod(método de pago) existente",
        requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                content = @Content(
                        mediaType = "application/json",
                        examples = @ExampleObject(
                                value = """
                                        {
                                            "id": 2,
                                            "category": "Credit Card",
                                            "detail": "MasterCard"
                                        }
                                        """
                        )
                )
        )
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Método de pago actualizado"),
    })
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/private/update")
    public ResponseEntity<PaymentMethod> updatePaymentMethod(@RequestBody PaymentMethod paymentMethod) {
        PaymentMethod paymentMethodUpdated= paymentMethodService.updatePayment(paymentMethod);
        return ResponseEntity.ok(paymentMethodUpdated);
    }

    @Operation(summary = "Obtener Método de pago por Id", description = "Devuelve un Método de pago basado en Id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Método de pago encontrado"),
            @ApiResponse(responseCode = "404", description = "Método de pago no encontrado")
    })
    @GetMapping("/public/{id}")
    public ResponseEntity<PaymentMethod> getPaymentById(@Parameter(description = "ID del método de pago a obtener", example = "1") @PathVariable Long id) {
        Optional<PaymentMethod> paymentMethod = paymentMethodService.getPaymentById(id);
        return paymentMethod.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Obtener todos los métodos de pago", description = "Devuelve un Set de todos los métodos de pago")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200")
    })
    @GetMapping("/public/all")
    public ResponseEntity<List<PaymentMethod>> getAllPayments() {
        List<PaymentMethod> paymentMethodList = paymentMethodService.getAllPayments();
        return ResponseEntity.ok(paymentMethodList);
    }

    @Operation(summary = "Obtener método de pago por ID de ticket", description = "Devuelve un método de pago por ID de ticket")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200")
    })
    @GetMapping("/private/ticket/{id}")
    public ResponseEntity<Optional<PaymentMethod>> getPaymentsByTicketId(@Parameter(description = "ID del ticket para obtener el método de pago", example = "1") @PathVariable Long id) {
        Optional<PaymentMethod> paymentMethod = paymentMethodService.getPaymentsByTicketId(id);
        return ResponseEntity.ok(paymentMethod);
    }

    @Operation(summary = "Obtener método de pago por categoría", description = "Devuelve una lista de métodos de pago por su categoría")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200")
    })
    @GetMapping("/private/category")
    public ResponseEntity<List<PaymentMethod>> getAllPaymentsByCategory(@Parameter(description = "Categoría del método de pago para obtener todos los métodos", example = "Credit Card") @RequestParam("category") String category) {
        List<PaymentMethod> paymentMethod = paymentMethodService.getAllPaymentsByCategory(category);
        return ResponseEntity.ok(paymentMethod);
    }

    @Operation(summary = "Eliminar método de pago", description = "Elimina el método de pago basado en su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "203", description = "Evento eliminado")
    })
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/private/delete/{id}")
    public ResponseEntity<Void> deletePaymentMethod(@PathVariable Long id) {
        paymentMethodService.deletePayment(id);
        return ResponseEntity.noContent().build();
    }
}
