package tests;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;
import common.CommonFunctions;
import model.GroupData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class GroupCreationTests extends TestBase {

    public static List<GroupData> groupProvider() throws IOException {
        var result = new ArrayList<>(List.of(
                new GroupData("", CommonFunctions.randomStringWithNumbers(1000), CommonFunctions.randomStringWithNumbers(1000), CommonFunctions.randomStringWithNumbers(1000)),
                new GroupData("", "name" + CommonFunctions.randomStringWithNumbers(1000),
                        "header" + CommonFunctions.randomStringWithNumbers(1000),
                        "footer" + CommonFunctions.randomStringWithNumbers(1000)),
                new GroupData("", "name .,/-+;:?\"@#!$%^&*()_=", "header .,/-+;:?\"@#!$%^&*()_=",
                        "footer .,/-+;:?\"@#!$%^&*()_=")
        ));
//        for (var name : List.of("", "group name")) {
//            for (var header : List.of("", "group header")) {
//                for (var footer : List.of("", "group footer")) {
//                    result.add(new GroupData().withName(name).withHeader(header).withFooter(footer));
//                }
//            }
//        }
        var json = "";
        try (var reader = new FileReader("groups.json");
             var breader = new BufferedReader(reader)
        ) {
            var line = breader.readLine();
            while (line != null) {
                json = json + line;
                line = breader.readLine();
            }
        }
//        var json = Files.readString(Paths.get("groups.json"));
        ObjectMapper mapper = new ObjectMapper();
        var value = mapper.readValue(json, new TypeReference<List<GroupData>>() {
        });
//        var mapper = new YAMLMapper();
//        var value = mapper.readValue(new File("groups.yaml"), new TypeReference<List<GroupData>>() {
//        });
//        var mapper = new XmlMapper();
//        var value = mapper.readValue(new File("groups.xml"), new TypeReference<List<GroupData>>() {
//        });
        result.addAll(value);
        return result;
    }

    public static List<GroupData> negativeGroupProvider() {
        var result = new ArrayList<>(List.of(
                new GroupData().withName("group name'"),
                new GroupData().withHeader("group header'"),
                new GroupData().withFooter("group footer'")
        ));
        return result;
    }

    @ParameterizedTest
    @MethodSource("groupProvider")
    public void canCreateGroup(GroupData group) {
        var oldGroups = app.groups().getList();
        app.groups().createGroup(group);
        var newGroups = app.groups().getList();
        newGroups.sort(app.groups().compareById);
        var expectedGroups = new ArrayList<>(oldGroups);
        expectedGroups.add(group.withId(newGroups.get(newGroups.size() - 1).id()).withHeader("").withFooter(""));
        expectedGroups.sort(app.groups().compareById);
        Assertions.assertEquals(newGroups, expectedGroups);
    }

    @ParameterizedTest
    @MethodSource("negativeGroupProvider")
    public void cannotCreateGroup(GroupData group) {
        var oldGroups = app.groups().getList();
        app.groups().createGroup(group);
        var newGroups = app.groups().getList();
        Assertions.assertEquals(newGroups, oldGroups);
    }
}
