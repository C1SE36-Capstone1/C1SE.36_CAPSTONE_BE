package com.example.capstone.api.Payment;

import com.example.capstone.Config.VnPayConfig;
import com.example.capstone.dto.CartWithDetail;
import com.example.capstone.dto.reponse.PaymentResponse;
import com.example.capstone.model.Cart.Cart;
import com.example.capstone.model.Cart.CartDetail;
import com.example.capstone.model.Payment.Payment;
import com.example.capstone.service.ICartDetailService;
import com.example.capstone.service.ICartService;
import com.example.capstone.service.IPaymentService;
import com.example.capstone.service.Impl.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/payment")
public class PaymentAPI {
    @Autowired
    private  ICartService cartService;
    @Autowired
    private EmailService emailService;
    @Autowired
    private ICartDetailService cartDetailService;
    @Autowired
    private  IPaymentService paymentService;

    /**
     * API: http://localhost:8080/api/payment
     *

    */
    @PutMapping("")
    public ResponseEntity<PaymentResponse> createPayment(@RequestBody CartWithDetail cartWithDetail) throws UnsupportedEncodingException {
        Cart cart = cartWithDetail.getCart();
        List<CartDetail> cartDetailList = cartWithDetail.getCartDetailList();
        int totalAmount = 0;
        Set<CartDetail> cartDetails = new HashSet<>();
        this.cartService.update(cart);
        for (CartDetail cartDetail : cartDetailList) {
            if (!cartDetail.isStatus()) {
                totalAmount += cartDetail.getQuantity() * cartDetail.getProduct().getPrice();
                cartDetails.add(cartDetail);
            }
        }
        if (totalAmount != 0) {
            String vnp_TxnRef = VnPayConfig.getRandomNumber(8);
            String vnp_IpAddr = "127.0.0.1";
            String vnp_TmnCode = VnPayConfig.vnp_TmnCode;

            Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
            SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
            String vnp_CreateDate = formatter.format(cld.getTime());

            Map<String, String> vnp_Params = new HashMap<>();
            vnp_Params.put("vnp_Amount", totalAmount + "00");
            vnp_Params.put("vnp_Command", VnPayConfig.vnp_Command);
            vnp_Params.put("vnp_CreateDate", vnp_CreateDate);
            vnp_Params.put("vnp_CurrCode", "VND");
            vnp_Params.put("vnp_IpAddr", vnp_IpAddr);
            vnp_Params.put("vnp_Locale", "vn");
            vnp_Params.put("vnp_OrderInfo", "Thanh toan don hang:" + vnp_TxnRef);
            vnp_Params.put("vnp_OrderType", "topup");
            vnp_Params.put("vnp_ReturnUrl", VnPayConfig.vnp_ReturnUrl);
            vnp_Params.put("vnp_TmnCode", vnp_TmnCode);
            vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
            vnp_Params.put("vnp_Version", VnPayConfig.vnp_Version);

            List fieldNames = new ArrayList(vnp_Params.keySet());
            Collections.sort(fieldNames);
            StringBuilder hashData = new StringBuilder();
            StringBuilder query = new StringBuilder();
            Iterator itr = fieldNames.iterator();
            while (itr.hasNext()) {
                String fieldName = (String) itr.next();
                String fieldValue = (String) vnp_Params.get(fieldName);
                if ((fieldValue != null) && (fieldValue.length() > 0)) {
                    //Build hash data
                    hashData.append(fieldName);
                    hashData.append('=');
                    hashData.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                    //Build query
                    query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII.toString()));
                    query.append('=');
                    query.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                    if (itr.hasNext()) {
                        query.append('&');
                        hashData.append('&');
                    }
                }
            }

            String queryUrl = query.toString();
            String vnp_SecureHash = VnPayConfig.hmacSHA512(VnPayConfig.vnp_HashSecret, hashData.toString());
            queryUrl += "&vnp_SecureHash=" + vnp_SecureHash;
            String paymentUrl = VnPayConfig.vnp_PayUrl + "?" + queryUrl;

            Payment payment = new Payment();
            payment.setCartDetails(cartDetails);
            payment.setTotalAmount(totalAmount);
            payment.setIsPaid(false);
            payment.setTnxRef(vnp_TxnRef);
            payment.setCartId(cart.getCartId());
            this.paymentService.update(payment);

            PaymentResponse paymentResponse = new PaymentResponse();
            paymentResponse.setStatus("OK");
            paymentResponse.setMessage("Successfully");
            paymentResponse.setUrl(paymentUrl);


            return new ResponseEntity<>(paymentResponse, HttpStatus.OK);
        } else
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/transaction/{tnxRef}")
    public ResponseEntity<?> transactionChecking(@PathVariable("tnxRef") String tnxRef) {
        Payment payment = this.paymentService.findPaymentByTnxRef(tnxRef);
        if (!payment.isPaid()) {
            payment.setIsPaid(true);
            this.paymentService.update(payment);
            long totalAmount = payment.getTotalAmount();
            Cart cart = this.cartService.findById(payment.getCartId());
            List<CartDetail> cartDetails = new ArrayList<>(payment.getCartDetails());
            for (CartDetail cartDetail : cartDetails) {
                cartDetail.setStatus(true);
                this.cartDetailService.update(cartDetail);
            }
            this.emailService.emailProcess(cart, totalAmount, cartDetails);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/fail/{tnxRef}")
    public ResponseEntity<?> transactionFail(@PathVariable("tnxRef") String tnxRef) {
        this.paymentService.deleteByTnxRef(tnxRef);
        return new ResponseEntity<>(HttpStatus.GONE);
    }

}
