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

        UserDao userDao = new UserDao();
        Map<String, String> errors = new HashMap<>();

        // ===== 必須チェック =====
        if (isEmpty(userName)) errors.put("1", "ユーザー名は必須です");
        if (isEmpty(realName)) errors.put("2", "氏名は必須です");
        if (isEmpty(birthDateStr)) errors.put("3", "生年月日は必須です");
        if (isEmpty(address)) errors.put("4", "住所は必須です");
        if (isEmpty(tel)) errors.put("5", "電話番号は必須です");
        if (isEmpty(password)) errors.put("6", "パスワードは必須です");

        // ===== 形式チェック（JSPのルールと合わせる）=====
        if (!isEmpty(userName) && !userName.matches("^[A-Za-z0-9ぁ-んァ-ヶ一-龥ー_]{1,20}$")) {
            errors.put("7", "ユーザー名は1〜20文字（ひらがな/カタカナ/漢字/英数字/_）で入力してください");
        }

        if (!isEmpty(realName) && !realName.matches("^[A-Za-zぁ-んァ-ヶ一-龥ー\\s]{1,30}$")) {
            errors.put("8", "氏名は1〜30文字（漢字/かな/英字/スペース）で入力してください");
        }

        if (!isEmpty(address) && address.length() > 60) {
            errors.put("9", "住所は60文字以内で入力してください");
        }

        // 電話番号：0から始まる10〜11桁（ハイフンなし）
        if (!isEmpty(tel) && !tel.matches("^0\\d{9,10}$")) {
            errors.put("10", "電話番号はハイフンなしで、0から始まる10〜11桁の数字で入力してください");
        }

        // パスワード：8〜32、英字+数字（記号なし）
        if (!isEmpty(password) && !password.matches("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,32}$")) {
            errors.put("11", "パスワードは8〜32文字で、英字と数字を両方含めてください（記号なし）");
        }

        // ===== 生年月日チェック（00日/00月を弾く＋実在日付チェック）=====
        LocalDateTime birthDateTime = null;
        if (!isEmpty(birthDateStr)) {

            // まず形式（YYYY-MM-DD）をチェック
            if (!birthDateStr.matches("^\\d{4}-\\d{2}-\\d{2}$")) {
                errors.put("12", "生年月日はYYYY-MM-DD形式で入力してください");
            } else {
                try {
                    String[] parts = birthDateStr.split("-");
                    int year = Integer.parseInt(parts[0]);
                    int month = Integer.parseInt(parts[1]);
                    int day = Integer.parseInt(parts[2]);

                    // 00月・00日を明示的にNG
                    if (month == 0 || day == 0) {
                        errors.put("12", "生年月日は正しい日付を入力してください");
                    } else {
                        // 実在する日付かチェック（例：2/30は例外になる）
                        LocalDate birthDate = LocalDate.of(year, month, day);
                        birthDateTime = birthDate.atStartOfDay();
                    }
                } catch (Exception e) {
                    errors.put("12", "生年月日は正しい日付を入力してください");
                }
            }
        }

        // 電話番号重複チェック
        if (errors.isEmpty() && !isEmpty(tel)) {
            if (userDao.getByTel(tel) != null) {
                errors.put("13", "この電話番号は既に登録されています");
            }
        }

        // エラーがある場合
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

        // 登録
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
