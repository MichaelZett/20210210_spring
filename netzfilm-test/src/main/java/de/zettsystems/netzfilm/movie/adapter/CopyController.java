package de.zettsystems.netzfilm.movie.adapter;

import de.zettsystems.netzfilm.movie.application.CopyService;
import de.zettsystems.netzfilm.movie.domain.Copy;
import de.zettsystems.netzfilm.movie.domain.CopyType;
import de.zettsystems.netzfilm.movie.domain.MovieRepository;
import de.zettsystems.netzfilm.movie.values.CopyToOrder;
import de.zettsystems.netzfilm.movie.values.OrderableMovie;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class CopyController {
    private final CopyService copyService;
    private final MovieRepository movieRepository;

    public CopyController(CopyService copyService, MovieRepository movieRepository) {
        this.copyService = copyService;
        this.movieRepository = movieRepository;
    }

    @ModelAttribute
    public void addCopyTypes(Model model) {
        model.addAttribute("types", CopyType.values());
    }

    @GetMapping("/copy")
    public String copy(Model model, HttpServletRequest request) {
        final List<OrderableMovie> movies = movieRepository.findAll().stream().map(m -> new OrderableMovie(m.getId(), m.getTitle())).collect(Collectors.toUnmodifiableList());
        model.addAttribute("movies", movies);
        model.addAttribute("copy", new CopyToOrder());
        Map<String, ?> inputFlashMap = RequestContextUtils.getInputFlashMap(request);
        if (inputFlashMap != null) {
            model.addAttribute("message", inputFlashMap.get("message"));
        }
        return "copy";
    }

    @PostMapping("/copy")
    public String addCopy(CopyToOrder copyToOrder, RedirectAttributes redirectAttributes) {
        Copy copy = copyService.createCopy(copyToOrder.getMovieId(), copyToOrder.getType());
        redirectAttributes.addFlashAttribute("message", "Hallo, Du hast eine " + copy.getType() + " von '" + copy.getMovie().getTitle() + "' bestellt. Danke!");
        return "redirect:/copy";
    }

}