package user.main;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.User;
import dao.UserDao;
import tool.Action;

public class UserRegistExecuteAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

        String userName = req.getParameter("userName");
        String realName = req.getParameter("realName");
        String birthDateStr = req.getParameter("birthDate");
        String address = req.getParameter("address");
        String tel = req.getParameter("tel");
        String password = req.getParameter("password");

        // ✅ 念のため trim（空白事故対策）
        if (userName != null) userName = userName.trim();
        if (realName != null) realName = realName.trim();
        if (address != null) address = address.trim();
        if (tel != null) tel = tel.trim();
        if (password != null) password = password.trim();

        UserDao userDao = new UserDao();
        Map<String, String> errors = new HashMap<>();

        // ユーザー名：1〜20文字（ひら/カタ/漢字/英数字/_）
        if (!isEmpty(userName) && !userName.matches("^[A-Za-z0-9ぁ-んァ-ヶ一-龥ー_]{1,20}$")) {
            errors.put("7", "ユーザー名は1〜20文字（ひらがな/カタカナ/漢字/英数字/_）で入力してください");
        }

        // 氏名：1〜30文字（漢字/かな/英字/スペース）
        if (!isEmpty(realName) && !realName.matches("^[A-Za-zぁ-んァ-ヶ一-龥ー\\s]{1,30}$")) {
            errors.put("8", "氏名は1〜30文字（漢字/かな/英字/スペース）で入力してください");
        }

        // 住所：60文字以内
        if (!isEmpty(address) && address.length() > 60) {
            errors.put("9", "住所は60文字以内で入力してください");
        }

        // 電話番号：0から始まる10〜11桁（ハイフンなし）
        boolean telFormatOk = !isEmpty(tel) && tel.matches("^0\\d{9,10}$");
        if (!isEmpty(tel) && !telFormatOk) {
            errors.put("10", "電話番号は0から始まる10〜11桁の半角数字で入力してください");
        }

        // ✅ telが形式OKなら、他の項目がエラーでも必ず重複チェック
        if (telFormatOk) {
            if (userDao.getByTel(tel) != null) {
                errors.put("13", "この電話番号は既に登録されています");
            }
        }

        // パスワード：8〜32、英字+数字（記号なし）
        if (!isEmpty(password) && !password.matches("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,32}$")) {
            errors.put("11", "パスワードは8〜32文字で、英字と数字を両方含めてください（記号なし）");
        }

        // 生年月日チェック（00日/00月を弾く＋実在日付チェック）
        LocalDateTime birthDateTime = null;
        if (!isEmpty(birthDateStr)) {

            // 形式（YYYY-MM-DD）
            if (!birthDateStr.matches("^\\d{4}-\\d{2}-\\d{2}$")) {
                errors.put("12", "生年月日はYYYY-MM-DD形式で入力してください");
            } else {
                try {
                    String[] parts = birthDateStr.split("-");
                    int year  = Integer.parseInt(parts[0]);
                    int month = Integer.parseInt(parts[1]);
                    int day   = Integer.parseInt(parts[2]);

                    if (month == 0 || day == 0) {
                        errors.put("12", "生年月日は正しい日付を入力してください");
                    } else {
                        LocalDate birthDate = LocalDate.of(year, month, day); // 実在チェック
                        birthDateTime = birthDate.atStartOfDay();
                    }
                } catch (Exception e) {
                    errors.put("12", "生年月日は正しい日付を入力してください");
                }
            }
        }

        // ==========================
        // エラーがある場合
        // ==========================
        if (!errors.isEmpty()) {
            req.setAttribute("errors", errors);

            // 入力値を戻す
            req.setAttribute("userName", userName);
            req.setAttribute("realName", realName);
            req.setAttribute("birthDate", birthDateStr);
            req.setAttribute("address", address);
            req.setAttribute("tel", tel);

            req.getRequestDispatcher("UserRegist.action").forward(req, res);
            return;
        }

        // ==========================
        // 登録
        // ==========================
        User user = new User();
        user.setUserName(userName);
        user.setRealName(realName);
        user.setBirthDate(birthDateTime);
        user.setAddress(address);
        user.setTelNumber(tel);
        user.setPassword(password);

        boolean result = userDao.insert(user);

        if (result) {
            req.getRequestDispatcher("user_regist_complete.jsp").forward(req, res);
        } else {
            errors.put("99", "登録に失敗しました");
            req.setAttribute("errors", errors);
            req.getRequestDispatcher("UserRegist.action").forward(req, res);
        }
    }

    private boolean isEmpty(String s) {
        return s == null || s.trim().isEmpty();
    }
}
