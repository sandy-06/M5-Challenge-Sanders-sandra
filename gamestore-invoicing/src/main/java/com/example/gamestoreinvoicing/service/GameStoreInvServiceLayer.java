package com.example.gamestoreinvoicing.service;

import com.example.gamestoreinvoicing.model.*;
import com.example.gamestoreinvoicing.repository.InvoiceRepository;
import com.example.gamestoreinvoicing.repository.ProcessingFeeRepository;
import com.example.gamestoreinvoicing.repository.TaxRepository;
import com.example.gamestoreinvoicing.util.feign.GameStoreClient;
import com.example.gamestoreinvoicing.viewModel.InvoiceViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@FeignClient(name = "gamestore-catalog")
@Component
@Service
public class GameStoreInvServiceLayer {
    private final BigDecimal PROCESSING_FEE = new BigDecimal("15.49");
    private final BigDecimal MAX_INVOICE_TOTAL = new BigDecimal("999.99");
    private final BigDecimal TAX_RATE = new BigDecimal("0.06");


    InvoiceRepository invoiceRepo;
    TaxRepository taxRepo;
    ProcessingFeeRepository processingFeeRepo;
    GameStoreClient gameStoreClient;

    @Autowired
    public GameStoreInvServiceLayer(InvoiceRepository invoiceRepo, TaxRepository taxRepo, ProcessingFeeRepository processingFeeRepo, GameStoreClient gameStoreClient) {

        this.invoiceRepo = invoiceRepo;
        this.taxRepo = taxRepo;
        this.processingFeeRepo = processingFeeRepo;
        this.gameStoreClient = gameStoreClient;
    }

    public InvoiceViewModel createInvoice(InvoiceViewModel invoiceViewModel) {

        //validation...
        if (invoiceViewModel == null)
            throw new NullPointerException("Create invoice failed. no invoice data.");

        if (invoiceViewModel.getItemType() == null)
            throw new IllegalArgumentException("Unrecognized Item type. Valid ones: Console or Game");

        //Check Quantity is > 0...
        if (invoiceViewModel.getQuantity() <= 0) {
            throw new IllegalArgumentException(invoiceViewModel.getQuantity() +
                    ": Unrecognized Quantity. Must be > 0.");
        }
        if(Console= invoiceViewModel.getItemType()) {
            Console console = gameStoreClient.getConsoleById(invoiceViewModel.getItemId());
            if (console == null)
                throw new IllegalArgumentException("Unrecognized Console Id: " + invoiceViewModel.getItemId());
        } else if (Game = invoiceViewModel.getItemType()) {
            Game game = gameStoreClient.getGameById(invoiceViewModel.getItemId());
            if (game == null)
                throw new IllegalArgumentException("Unrecognized Game Id: " + invoiceViewModel.getItemId());
        } else {
            throw new IllegalArgumentException("Unrecognized Item type. Valid ones: Console or Game");
        }

        //start building invoice...
        Invoice invoice = new Invoice();
        invoice.setName(invoiceViewModel.getName());
        invoice.setStreet(invoiceViewModel.getStreet());
        invoice.setCity(invoiceViewModel.getCity());
        invoice.setState(invoiceViewModel.getState());
        invoice.setZipcode(invoiceViewModel.getZipcode());
        invoice.setItemType(invoiceViewModel.getItemType());
        invoice.setItemId(invoiceViewModel.getItemId());

        //Checks the item type and get the correct unit price
        //Check if we have enough quantity
        //Api call to get the unit price of the item
        if(invoiceViewModel.getItemType().equals("Console")) {
            Console tempCon = null;
            Optional<Console> returnVal = Optional.ofNullable(gameStoreClient.getConsoleById(invoiceViewModel.getItemId()));
            tempCon = gameStoreClient.getConsoleById(invoiceViewModel.getItemId());
            if (tempCon == null)
                throw new IllegalArgumentException("Console not found.");

            if (tempCon.isPresent()) {
                tempCon = returnVal.get();
            } else {
                throw new IllegalArgumentException("Requested item is unavailable.");
            }

            if (invoiceViewModel.getQuantity() > tempCon.getQuantity()) {
                throw new IllegalArgumentException("Requested quantity is unavailable.");
            }

            invoice.setUnitPrice(tempCon.getPrice());

        } else if (invoiceViewModel.getItemType().equals("Game")) {
            Game tempGame = null;
            tempGame = gameStoreClient.getGameById(gameStoreClient.getGameById());
            Optional<Game> returnVal = gameStoreClient.getGameById(gameStoreClient.getGameById());


        if (tempGame == null)
            throw new IllegalArgumentException("Game not found.");

        if (returnVal.isPresent()) {
            tempGame = returnVal.get();
        } else {
            throw new IllegalArgumentException("Requested item is unavailable.");
        }

        if (invoiceViewModel.getQuantity() > tempGame.getQuantity()) {
            throw new IllegalArgumentException("Requested quantity is unavailable.");
        }
        invoice.setUnitPrice(tempGame.getPrice());

    } else if(GameStoreClient.getItemType().

    equals("TShirt"))

    {
        TShirt tempTShirt = null;
        tempTShirt = gameStoreClient.getTShirtsById(GameStoreClient.getItemId());
        Optional<TShirt> returnVal = Optional.ofNullable(gameStoreClient.getTShirtsById(GameStoreClient.getItemId()));

        if (returnVal.isPresent()) {
            tempTShirt = returnVal.get();
        } else {
            throw new IllegalArgumentException("Requested item is unavailable.");
        }

        if (GameStoreClient.getQuantity() > tempTShirt.getQuantity()) {
            throw new IllegalArgumentException("Requested quantity is unavailable.");
        }
        invoice.setUnitPrice(tempTShirt.getPrice());

    } else

    {
        throw new IllegalArgumentException(GameStoreClient.getItemType() +
                ": Unrecognized Item type. Valid ones: T-Shirt, Console, or Game");
    }

    //Validate State and Calc tax...
    BigDecimal tempTaxRate;
    Optional<Tax> returnVal = taxRepo.findById(invoice.getState());

        if(returnVal.isPresent())

    {
        tempTaxRate = returnVal.get().getRate();
    } else if(invoice.getState() == null)
    {
        tempTaxRate = new BigDecimal("0");
    } else if(invoice.getState() == "") {
        tempTaxRate = new BigDecimal("0");
    } else {
        throw new IllegalArgumentException("Unrecognized State. Valid ones: " +
                "AL, AK, AZ, AR, CA, CO, CT, DE, FL, GA, HI, ID, IL, IN, IA, KS, KY, LA, ME, MD, MA, MI, MN, MS, MO, MT, NE, NV, NH, NJ, NM, NY, NC, ND, OH, OK, OR, PA, RI, SC, SD, TN, TX, UT, VT, VA, WA, WV, WI, WY");
    }

    BigDecimal processingFee;
    Optional<ProcessingFee> returnVal2 = processingFeeRepo.findById(InvoiceViewModel.getItemType());

        if(returnVal2.isPresent())

    {
        processingFee = returnVal2.get().getFee();
    } else

    {
        throw new IllegalArgumentException("Requested item is unavailable.");
    }

        Invoice.setProcessingFee(processingFee);

    //Checks if quantity of items if greater than 10 and adds additional processing fee
        if(InvoiceViewModel.getQuantity()>10)

    {
        invoice.setProcessingFee(invoice.getProcessingFee().add(PROCESSING_FEE));
    }

        invoice.setTotal(invoice.getSubtotal().

    add(invoice.getProcessingFee()).

    add(invoice.getTax()));

    //checks total for validation
        if((invoice.getTotal().

    compareTo(MAX_INVOICE_TOTAL) >0))

    {
        throw new IllegalArgumentException("Subtotal exceeds maximum purchase price of $999.99");
    }

    invoice =invoiceRepo.save(invoice);

        return

    buildInvoiceViewModel(invoice);

}

    public InvoiceViewModel getInvoice(long id) {
        Optional<Invoice> invoice = invoiceRepo.findById(id);
        if (invoice == null)
            return null;
        else
            return buildInvoiceViewModel(invoice.get());
    }

    public List<InvoiceViewModel> getAllInvoices() {
        List<Invoice> invoiceList = invoiceRepo.findAll();
        List<InvoiceViewModel> ivmList = new ArrayList<>();
        List<InvoiceViewModel> exceptionList = null;

        if (invoiceList == null) {
            return exceptionList;
        } else {
            invoiceList.stream().forEach(i -> {
                ivmList.add(buildInvoiceViewModel(i));
            });
        }
        return ivmList;
    }

    public List<InvoiceViewModel> getInvoicesByCustomerName(String name) {
        List<Invoice> invoiceList = invoiceRepo.findByName(name);
        List<InvoiceViewModel> ivmList = new ArrayList<>();
        List<InvoiceViewModel> exceptionList = null;

        if (invoiceList == null) {
            return exceptionList;
        } else {
            invoiceList.stream().forEach(i -> ivmList.add(buildInvoiceViewModel(i)));
        }
        return ivmList;
    }

    public void deleteInvoice(long id) {
        invoiceRepo.deleteById(id);
    }

    //Game service layer...

    public InvoiceViewModel buildInvoiceViewModel(Invoice invoice) {
        InvoiceViewModel invoiceViewModel = new InvoiceViewModel();
        invoiceViewModel.setId(invoice.getId());
        invoiceViewModel.setName(invoice.getName());
        invoiceViewModel.setStreet(invoice.getStreet());
        invoiceViewModel.setCity(invoice.getCity());
        invoiceViewModel.setState(invoice.getState());
        invoiceViewModel.setZipcode(invoice.getZipcode());
        invoiceViewModel.setItemType(invoice.getItemType());
        invoiceViewModel.setItemId(invoice.getItemId());
        invoiceViewModel.setUnitPrice(invoice.getUnitPrice());
        invoiceViewModel.setQuantity(invoice.getQuantity());
        invoiceViewModel.setSubtotal(invoice.getSubtotal());
        invoiceViewModel.setProcessingFee(invoice.getProcessingFee());
        invoiceViewModel.setTax(invoice.getTax());
        invoiceViewModel.setProcessingFee(invoice.getProcessingFee());
        invoiceViewModel.setTotal(invoice.getTotal());

        return invoiceViewModel;
    }

}
