package mycafe.MyCafeWeb;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import mycafe.MyCafeWeb.model.Cafe; 

@Controller
public class CafeController {

    private List<Cafe> cafeList = new ArrayList<>();

    public CafeController() {
        cafeList.add(new Cafe("純喫茶サンプル", "東京都新宿区", 5));
        cafeList.add(new Cafe("カフェ・ド・リエ", "東京都渋谷区", 4));
    }

    @GetMapping("/")
    public String index(Model model) {
        
        model.addAttribute("cafes", cafeList); 
        
        return "index";}
    @GetMapping("/new")
      public String newCafeForm() {
          return "new-cafe";
    }
    @PostMapping("/create") // "/create" への "POST" リクエストを処理
    public String createCafe(
            @RequestParam("name") String name,       // フォームの "name" を受け取る
            @RequestParam("address") String address, // フォームの "address" を受け取る
            @RequestParam("rating") int rating) {   // フォームの "rating" を受け取る
        
        // コンソールアプリの時と同じロジック！
        Cafe newCafe = new Cafe(name, address, rating);
        cafeList.add(newCafe);
        
        // 登録が終わったら、トップページ("/")にリダイレクト（自動で移動）する
        return "redirect:/";
    }
}