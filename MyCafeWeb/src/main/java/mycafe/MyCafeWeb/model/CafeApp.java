package mycafe.MyCafeWeb.model;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CafeApp {

    public static void main(String[] args) {
    	
//    	List<Cafe> cafeList = new ArrayList<>();
    	List<Cafe> cafeList = loadData(); // ★起動時にデータを読み込む
    	
    	Scanner scanner = new Scanner(System.in);
    	
    	while (true) {
    		System.out.println("\n--- My Cafe メニュー ---");
    		System.out.println("1: 喫茶店を登録する");
            System.out.println("2: 登録済みの喫茶店を見る");
            System.out.println("3: 訪問を記録する");
            System.out.println("4: 喫茶店の詳細を見る");
            System.out.println("5: 喫茶店の情報を編集する");
            System.out.println("6: 喫茶店を削除する");
            System.out.println("9: アプリを終了する");
            System.out.print("操作を選んでください > ");
    		
            int choice = scanner.nextInt();
            
            if (choice == 1) {
//            	System.out.println("（喫茶店登録機能は未実装です）");
            	System.out.println("店名を入力してください > ");
            	String name = scanner.next();
            	
            	System.out.println("住所を入力してください > ");
            	String address = scanner.next();
            	
            	System.out.println("評価を入力してください > ");
            	int rating = scanner.nextInt();
            	
            	Cafe newCafe = new Cafe(name,address,rating);
            	
            	cafeList.add(newCafe);
            	
            	System.out.println("「"+ newCafe.getName() + "」を登録しました。");
            } else if (choice == 2) {
//                System.out.println("（一覧表示機能は未実装です）");
                if (cafeList.isEmpty()) {
                	System.out.println("まだ喫茶店が登録されていません。");
                } else {
                	System.out.println("\n--- 登録済みの喫茶店一覧 ---");
                	
                	for (Cafe cafe : cafeList) {
                		System.out.println("店名: " + cafe.getName());
                        System.out.println("住所: " + cafe.getAddress());
                        System.out.println("評価: ★" + cafe.getRating()); 
                        System.out.println("--------------------");
                	}
                }
            } else if (choice == 3) {
            	System.out.println("訪問を記録する喫茶店を選んでください。");
            	
            	for (int i = 0; i < cafeList.size(); i++) {
            		System.out.println((i + 1) + ": " + cafeList.get(i).getName());
            	}
            	
            	System.out.println("番号を入力 > ");
            	int cafeNum = scanner.nextInt();
            	scanner.nextLine();
            	Cafe selectedCafe = cafeList.get(cafeNum - 1);
            	
            	System.out.println("コメントを入力してください。");
            	String comment = scanner.nextLine();
            	
            	VisitRecord newRecord = new VisitRecord(LocalDate.now());
            	newRecord.setComment(comment);
            	while (true) {
                    System.out.println("\n--- 注文メニューの登録 ---");
                    System.out.println("1: 飲み物を登録");
                    System.out.println("2: 食べ物を登録");
                    System.out.println("9: 登録完了");
                    System.out.print("操作を選んでください > ");
                    
                    int menuChoice = scanner.nextInt();
                    scanner.nextLine(); 

                    if (menuChoice == 1) {
                        System.out.print("飲み物名 > ");
                        String name = scanner.nextLine();
                        System.out.print("価格 > ");
                        int price = scanner.nextInt();
                        System.out.print("ホットですか？ (true/false) > ");
                        boolean isHot = scanner.nextBoolean();
                        System.out.println("備考");
                        String text = scanner.nextLine();
                        scanner.nextLine(); 

                        Drink newDrink = new Drink(name, price, isHot, text);
                        newRecord.addMenuItem(newDrink); 
                        System.out.println("「" + name + "」を登録しました。");

                    } else if (menuChoice == 2) {
                        System.out.println("（食べ物登録は未実装です）");

                    } else if (menuChoice == 9) {
                        System.out.println("メニューの登録を完了します。");
                        break; 
                    } else {
                        System.out.println("無効な選択です。");
                    }
                }
            	selectedCafe.addVisitRecord(newRecord);
            	
            	System.out.println(selectedCafe.getName() + "に訪問記録を追加しました。");
              
            } else if (choice == 4) {
            	System.out.println("詳細を見る喫茶店を選んでください。");
            	
            	if (cafeList.isEmpty()) {
                    System.out.println("まだ喫茶店が登録されていません。");
                } else {
                    for (int i = 0; i < cafeList.size(); i++) {
                        System.out.println((i + 1) + ": " + cafeList.get(i).getName());
                    }

                    System.out.print("番号を入力 > ");
                    int cafeNum = scanner.nextInt();
                    scanner.nextLine(); 

                    Cafe selectedCafe = cafeList.get(cafeNum - 1);

                    System.out.println("\n--- 「" + selectedCafe.getName() + "」の詳細 ---");
                    System.out.println("店名: " + selectedCafe.getName());
                    System.out.println("住所: " + selectedCafe.getAddress());
                    System.out.println("評価: ★" + selectedCafe.getRating());
                    System.out.println("--------------------");

                    List<VisitRecord> records = selectedCafe.getVisitRecords();
                    if (records.isEmpty()) {
                        System.out.println("まだ訪問記録はありません。");
                    } else {
                        System.out.println("【訪問記録】");
                        for (VisitRecord record : records) {
                            System.out.println("  訪問日: " + record.getVisitDate());
                            System.out.println("  コメント: " + record.getComment());
                            
                            System.out.println("  [注文したメニュー]");
                            List<MenuItem> items = record.getOrderedItems();
                            if (items.isEmpty()) {
                                System.out.println("    - メニューの記録はありません");
                            } else {
                                for (MenuItem item : items) {
                                    System.out.println("    - " + item.getDetails());
                                }
                            }
                            System.out.println(); 
                        }
                    }
                }
            } else if (choice == 5) {
            	System.out.println("情報を編集する喫茶店を選択してください。");
            	
            	if (cafeList.isEmpty()) {
            		System.out.println("まだ喫茶店が登録されていません。");
            	} else {
            		for (int i = 0; i < cafeList.size(); i++) {
            			System.out.println((i * 1) + ": " + cafeList.get(i).getName());
            		}
            		System.out.print("番号を入力 > ");
                    int cafeNum = scanner.nextInt();
                    scanner.nextLine();
                    
                    Cafe selectedCafe = cafeList.get(cafeNum - 1);
                    
                    System.out.println("--- 「" + selectedCafe.getName() + "」の情報を編集します ---");
                    System.out.println("（変更しない項目は、何も入力せずにEnterを押してください）");

                    System.out.println("現在の店名: " + selectedCafe.getName());
                    System.out.print("新しい店名 > ");
                    String newName = scanner.nextLine();
                    if (!newName.isEmpty()) { // 入力が空でなければ
                        selectedCafe.setName(newName); // setterで更新
                    }

                    System.out.println("現在の住所: " + selectedCafe.getAddress());
                    System.out.print("新しい住所 > ");
                    String newAddress = scanner.nextLine();
                    if (!newAddress.isEmpty()) {
                        selectedCafe.setAddress(newAddress);
                    }

                    System.out.println("現在の評価: ★" + selectedCafe.getRating());
                    System.out.print("新しい評価 (1-5) > ");
                    String newRatingStr = scanner.nextLine();
                    if (!newRatingStr.isEmpty()) {
                        int newRating = Integer.parseInt(newRatingStr); // 文字列を数値に変換
                        selectedCafe.setRating(newRating);
                    }

                    System.out.println("情報を更新しました。");
                }
            } else if (choice == 6) {
            	System.out.println("削除する喫茶店を選んでください。");

                if (cafeList.isEmpty()) {
                    System.out.println("まだ喫茶店が登録されていません。");
                } else {
                    for (int i = 0; i < cafeList.size(); i++) {
                        System.out.println((i + 1) + ": " + cafeList.get(i).getName());
                    }

                    System.out.print("番号を入力 > ");
                    int cafeNum = scanner.nextInt();
                    scanner.nextLine(); 

                    if (cafeNum >= 1 && cafeNum <= cafeList.size()) {
                    
                    int index = cafeNum - 1; 

                    Cafe cafeToDelete = cafeList.get(index);

                    System.out.print("本当に「" + cafeToDelete.getName() + "」を削除しますか？ (y/n) > ");
                    String confirmation = scanner.nextLine();

                    if (confirmation.equalsIgnoreCase("y")) {
                        cafeList.remove(index);
                        System.out.println("削除しました。");
                    } else {
                        System.out.println("削除をキャンセルしました。");
                    }
                } else {
                	System.out.println("エラー: 1 から " + cafeList.size() + " の間の有効な番号を入力してください。");
    	          }
                }
             } else if (choice == 9) {
                    System.out.println("アプリを終了します。");
                    break; 
             } else {
                    System.out.println("無効な選択です。");
             }
    	}
    	saveData(cafeList); // ★終了時にデータを保存する
    	scanner.close();
    }
    private static void saveData(List<Cafe> list) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("mycafe.dat"))) {
            oos.writeObject(list); // リストを丸ごとファイルに書き出す
            System.out.println("データを保存しました。");
        } catch (IOException e) {
            System.out.println("データの保存に失敗しました。");
            e.printStackTrace(); // エラーの詳細を表示
        }
    }

    // ファイルからデータを読み込むメソッド
    private static List<Cafe> loadData() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("mycafe.dat"))) {
            System.out.println("データを読み込みました。");
            return (List<Cafe>) ois.readObject(); // ファイルからリストを丸ごと読み込む
        } catch (FileNotFoundException e) {
            System.out.println("保存データが見つかりません。新規に開始します。");
            return new ArrayList<>(); // ファイルがなければ新しい空のリストを返す
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("データの読み込みに失敗しました。");
            e.printStackTrace();
            return new ArrayList<>(); // 失敗した場合も空のリストを返す
        }
    }
}
