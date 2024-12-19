package br.com.puli;

import org.springframework.web.bind.annotation.*;

import java.util.concurrent.atomic.AtomicLong;
import br.com.puli.exceptions.UnsupportedMathOperationException;
import static java.util.Collections.replaceAll;

@RestController
public class MathController {

    private final AtomicLong counter = new AtomicLong();

    @RequestMapping(value = "/sum/{numberOne}/{numberTwo}", method = RequestMethod.GET)
    public Double sum(
            @PathVariable(value = "numberOne")
            String numberOne,
            @PathVariable(value = "numberTwo")
            String numberTwo) throws Exception{
        if(!isNumeric(numberOne) || !isNumeric(numberTwo)){
            throw new UnsupportedMathOperationException("Please set a numeric value");
        }
        return convertToDouble(numberOne) + convertToDouble(numberTwo);
    }

    private Double convertToDouble(String strnumber) {
        if(strnumber == null)return null;
        String number = strnumber.replaceAll(",",".");
        if(isNumeric(number)){
            return Double.parseDouble(number);
        }
        return 0D;
    }

    private boolean isNumeric(String strnumber) {
        if(strnumber == null)return false;
        String number = strnumber.replaceAll(",",".");
        return number.matches("[+-]?[0-9]*\\.?[0-9]+");
    }
}
