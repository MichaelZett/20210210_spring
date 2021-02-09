package de.zettsystems.netzfilm.rent.adapter;

import de.zettsystems.netzfilm.movie.application.CopyService;
import de.zettsystems.netzfilm.movie.values.RentableCopy;
import de.zettsystems.netzfilm.rent.application.RentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Controller
public class RentController {
    private static final Logger LOG = LoggerFactory.getLogger(RentController.class);
    private final CopyService copyService;
    private final RentService rentService;

    public RentController(CopyService copyService, RentService rentService) {
        this.copyService = copyService;
        this.rentService = rentService;
    }

    @GetMapping("/rent")
    public String rent(Model model, HttpServletRequest request) {
        final List<RentableCopy> allRentableCopies = copyService.findAllRentableCopies();
        model.addAttribute("copies", allRentableCopies);
        model.addAttribute("rent", new RentableCopy());
        Map<String, ?> inputFlashMap = RequestContextUtils.getInputFlashMap(request);
        if (inputFlashMap != null) {
            model.addAttribute("message", inputFlashMap.get("message"));
        }
        return "rent";
    }

    @PostMapping("/rent")
    public String addUser(RentableCopy rent, RedirectAttributes redirectAttributes) {
        rentService.rentAMovie(2L, rent.getId(), LocalDate.now(), 7L);
        redirectAttributes.addFlashAttribute("message", "Hallo, Du hast '" + copyService.getTitleAndFormat(rent.getId()) + "' geliehen. Danke!");
        return "redirect:/rent";
    }

}