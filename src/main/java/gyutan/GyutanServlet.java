package gyutan;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import mygoogle.ai.studio.GoogleAIStudioAPI;

@WebServlet("/search")
public class GyutanServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.sendRedirect("index.html");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");

        String question = request.getParameter("question");

        if (question == null || question.trim().isEmpty()) {
            response.sendRedirect("index.html");
            return;
        }

        String prompt = "あなたは牛タン店の案内AIです。"
                + "以下のユーザーからの問い合わせ内容が、どのカテゴリに該当するか判定してください。\n"
                + "回答は必ず「概略」「メニュー」「通販」「その他」の4つの単語のうち、いずれか1つのみを出力してください。余計な説明や句読点は一切不要です。\n\n"
                + "・店舗の場所、地図、営業時間、定休日、アクセスに関する質問 → 概略\n"
                + "・店内で食べる料理、定食、単品、ドリンク、価格に関する質問 → メニュー\n"
                + "・お取り寄せ、オンライン購入、ギフト、発送、通販商品に関する質問 → 通販\n"
                + "・上記に当てはまらない質問 → その他\n\n"
                + "問い合わせ内容：" + question;

        GoogleAIStudioAPI api = new GoogleAIStudioAPI();
        String ans = api.getAnswer(prompt);

        if (ans == null) {
            response.sendRedirect("index.html");
            return;
        }
        ans = ans.trim();

        if (ans.contains("概略")) {
            response.sendRedirect("about.html");
        } else if (ans.contains("メニュー")) {
            response.sendRedirect("menu.html");
        } else if (ans.contains("通販")) {
            response.sendRedirect("shop.html");
        } else {
            response.sendRedirect("index.html");
        }
    }
}