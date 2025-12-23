package mycafe.MyCafeWeb;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import mycafe.MyCafeWeb.model.Cafe;
import mycafe.MyCafeWeb.model.VisitRecord; 

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
    
    @GetMapping("/cafe/{id}")
    public String detail(@PathVariable("id") int id, Model model) {
        Cafe cafe = cafeList.get(id);
        model.addAttribute("cafe", cafe);
        model.addAttribute("id", id); // 訪問記録フォームのURLで使うために必要
        return "detail";
    }

    @PostMapping("/cafe/{id}/record")
public String addRecord(
        @PathVariable("id") int id,
        @RequestParam("comment") String comment,
        @RequestParam("menuType") String menuType, // 追加
        @RequestParam("itemName") String itemName, // 追加
        @RequestParam("itemPrice") int itemPrice) { // 追加
    
    Cafe cafe = cafeList.get(id);
    VisitRecord record = new VisitRecord(LocalDate.now());
    record.setComment(comment);

    // ★ 科目Bで超重要な「多態性」のロジック
    if (!menuType.equals("none")) {
        if (menuType.equals("drink")) {
            // Drink（子）を作って MenuItem（親）のリストに入れる
            record.addMenuItem(new mycafe.MyCafeWeb.model.Drink(itemName, itemPrice, true, ""));
        } else if (menuType.equals("food")) {
            // Food（子）を作って MenuItem（親）のリストに入れる
            // ※Foodクラスが未完成なら、今はDrinkで代用するか、Foodを作成してください
        }
    }

    cafe.addVisitRecord(record);
    return "redirect:/cafe/" + id;
}
    @GetMapping("/cafe/{id}/edit")
    public String editCafeForm(@PathVariable("id") int id, Model model) {
        Cafe cafe = cafeList.get(id);
        model.addAttribute("cafe", cafe);
        model.addAttribute("id", id);
        return "edit-cafe";
    }

    // 2. 更新処理を実行する
    @PostMapping("/cafe/{id}/update")
    public String updateCafe(
            @PathVariable("id") int id,
            @RequestParam("name") String name,
            @RequestParam("address") String address,
            @RequestParam("rating") int rating) {
        
        // リストから対象のオブジェクトを取り出す（参照の取得）
        Cafe cafe = cafeList.get(id);
        
        // セッターを使って中身を書き換える（カプセル化と状態変化）
        cafe.setName(name);
        cafe.setAddress(address);
        cafe.setRating(rating);

        return "redirect:/cafe/" + id;
    }
    @PostMapping("/cafe/{id}/delete")
    public String deleteCafe(@PathVariable("id") int id) {
        // リストから指定したインデックスの要素を削除する
        // 科目Bでは、削除後に後ろの要素がどう詰まるかがポイントになります。
        cafeList.remove(id);
        
        return "redirect:/"; // 削除後は一覧へ戻る
    }
}