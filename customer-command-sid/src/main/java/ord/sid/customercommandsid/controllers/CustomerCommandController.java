package ord.sid.customercommandsid.controllers;


import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.eventsourcing.eventstore.EventStore;
import org.sid.coreapi.commands.CreateCustomerCommand;
import org.sid.coreapi.dtos.CustomerRequestDTO;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

@RestController
@AllArgsConstructor
@Slf4j
@RequestMapping("/customers/command")
public class CustomerCommandController
{

    private CommandGateway commandGateway;
    private EventStore eventStore ;

    @PostMapping("/create")
    private CompletableFuture<String> newCustomer(@RequestBody CustomerRequestDTO request){

        CompletableFuture<String> response = commandGateway.send(new CreateCustomerCommand(
                UUID.randomUUID().toString(),
                request.getName(),
                request.getEmail()
        ));
        return response;
    }

    @GetMapping(path="/eventStore/{customerId}")
    public Stream eventStore (@PathVariable String customerId){
        return  eventStore.readEvents(customerId).asStream();
    }

}
