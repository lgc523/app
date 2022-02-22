package dev.spider;

import dev.spider.configs.ProjectInfo;
import dev.spider.entity.Result;
import dnl.utils.text.table.TextTable;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.Objects;

/**
 * Hello world!
 */
@RestController
@EnableAspectJAutoProxy
@SpringBootApplication
public class App {
    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
//        System.out.println( "Hello World!" );
    }


    @Autowired
    ProjectInfo projectInfo;

    @GetMapping("info")
    public String info() {
        Map<String, String> map = projectInfo.getMap();

        StringBuilder sb = new StringBuilder();
        sb.append("\n[App info]");
        sb.append("\nApplication name : " + map.get("projectName"));
        sb.append("\nBuild version    : " + map.get("version"));
        sb.append("\nPort             : " + map.get("port"));
        sb.append("\nBuild name       : " + map.get("buildName"));
        sb.append("\nParent version   : " + map.get("parentVersion"));
        sb.append("\nBuild timestamp  : " + map.get("buildTime") + "\n");


        String[][] twoArrayObject = ProjectInfo.getTwoArrayObject(map);
        TextTable textTable = new TextTable(new String[]{"Metric", "value"}, twoArrayObject);
        textTable.printTable();
        return sb.toString();
    }

    /**
     * {@link Foo,Bar}
     *
     * @param route
     * @return
     */
    @GetMapping("check")
    public Result check(@RequestParam(value = "route") String route) {
        if (Objects.equals("1.0", route)) {
            return Result.success(new Foo("foo"));
        } else {
            return Result.success(new Bar("bar"));
        }
    }

    @Data
    public static class Foo {
        String name;

        public Foo(String name) {
            this.name = name;
        }
    }

    @Data
    public static class Bar {
        String val;

        public Bar(String val) {
            this.val = val;
        }
    }

}
