package dev.spider;

import dev.spider.configs.ProjectInfo;
import dev.spider.entity.Result;
import dnl.utils.text.table.TextTable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.Serializable;
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

        String sb = "\n[App info]" +
                "\nArtifactId       : " + map.get("artifactId") +
                "\nApplication name : " + map.get("projectName") +
                "\nBuild version    : " + map.get("version") +
                "\nBuild name       : " + map.get("buildName") +
                "\nPort             : " + map.get("port") +
                "\nBuild profile    : " + map.get("profile") +
                "\nRuntime  Name    : " + map.get("runtimeName") +
                "\nRuntime  Version : " + map.get("runtimeVersion") +
                "\nBuild timestamp  : " + map.get("buildTime") + "\n";


        String[][] twoArrayObject = ProjectInfo.getTwoArrayObject(map);
        TextTable textTable = new TextTable(new String[]{"Metric", "value"}, twoArrayObject);
        textTable.printTable();
        return sb;
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
    @NoArgsConstructor
    public static class Foo implements Serializable {
        String name;

        public Foo(String name) {
            this.name = name;
        }
    }

    @Data
    @NoArgsConstructor
    public static class Bar {
        String val;

        public Bar(String val) {
            this.val = val;
        }
    }

}
