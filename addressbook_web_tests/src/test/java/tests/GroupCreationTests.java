package tests;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import common.CommonFunctions;
import io.qameta.allure.Allure;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import model.GroupData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Feature("Groups")
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

    public static Stream<GroupData> randomGroupProvider() {
        Supplier<GroupData> randomGroup = () -> new GroupData()
                .withName(CommonFunctions.randomString(10))
                .withHeader(CommonFunctions.randomString(10))
                .withFooter(CommonFunctions.randomString(10));
        return Stream.generate(randomGroup).limit(3);
    }

    @Story("Create group")
    @ParameterizedTest
    @MethodSource("randomGroupProvider")
    public void canCreateGroup(GroupData group) {
        Allure.parameter("group", group);
        var oldGroups = app.hbm().getGroupsDBList();
        app.groups().createGroup(group);
        var newGroups = app.hbm().getGroupsDBList();
        Allure.step("Validating results from DB", step -> {
            var extraGroups = newGroups.stream().filter(g -> !oldGroups.contains(g)).collect(Collectors.toList());
            var newId = extraGroups.get(0).id();
            var expectedGroups = new ArrayList<>(oldGroups);
            expectedGroups.add(group.withId(newId));
            Assertions.assertEquals(Set.copyOf(newGroups), Set.copyOf(expectedGroups));
        });

        Allure.step("Validating results from UI", step -> {
            var newUIGroups = app.groups().getList();
            var expectedGroupsFromDB = new ArrayList<>();
            for (var newGroup : newGroups) {
                expectedGroupsFromDB.add(newGroup.withHeader("").withFooter(""));
            }
            Assertions.assertEquals(Set.copyOf(newUIGroups), Set.copyOf(expectedGroupsFromDB));
        });
    }

    @Story("Can not create uncorrect group, negative")
    @ParameterizedTest
    @MethodSource("negativeGroupProvider")
    public void cannotCreateGroup(GroupData group) {
        Allure.parameter("uncorrect group", group);
        var oldGroups = app.hbm().getGroupsDBList();
        app.groups().createGroup(group);
        var newGroups = app.hbm().getGroupsDBList();
        Allure.step("Validating results", step -> {
            Assertions.assertEquals(newGroups, oldGroups);
        });
    }
}
