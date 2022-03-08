package dev.spider.api;


import dev.spider.util.QrCodeUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;

@Controller
@RequestMapping("qr")
public class QrController {

    @RequestMapping("code")
    public void qrNoLogo(HttpServletResponse response) {

        String requestUrl = "http://192.168.5.175:8080/qr/cashier";
        try {
            OutputStream os = response.getOutputStream();
            QrCodeUtil.encode(requestUrl, null, os);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @RequestMapping("cashier")
    public String cashier(ModelMap modelMap) {
        String form = "";
        String action = form.substring(form.indexOf("action="));
        String biz_content = form.substring(form.indexOf("biz_content\" value"));

        String url = action.split(">")[0].split("action=")[1];
        String biz = biz_content.split(">")[0].split("value=")[1];

        System.out.println(url.substring(1, url.length() - 1));
        System.out.println(biz.substring(1, biz.length() - 1));
        modelMap.addAttribute("url", url.substring(1, url.length() - 1));
        modelMap.addAttribute("biz", biz.substring(1, biz.length() - 1));
        return "cashier";
    }
}
