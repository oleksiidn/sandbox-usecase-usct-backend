package global.govstack.mocksris.controller;

import global.govstack.mocksris.controller.dto.BeneficiaryDto;
import global.govstack.mocksris.model.Beneficiary;
import global.govstack.mocksris.service.PaymentService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/payment")
public class PaymentRestController {
    private final PaymentService paymentService;

    private final ModelMapper modelMapper;

    public PaymentRestController(PaymentService paymentService, ModelMapper modelMapper) {
        this.paymentService = paymentService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/emulator-health")
    public String getHealth() {
        return paymentService.health();
    }

    @PostMapping(value = "/order-payment")
    @ResponseStatus(HttpStatus.CREATED)
    public String create(@RequestBody final List<BeneficiaryDto> beneficiaryDtos) {
        List<Beneficiary> list = beneficiaryDtos.stream().map(this::convertToEntity).toList();
        paymentService.orderPayment(list);
        return "Thank you! Payment order received!";
    }

    private Beneficiary convertToEntity(BeneficiaryDto beneficiaryDto)  {
        return modelMapper.map(beneficiaryDto, Beneficiary.class);
    }
}
