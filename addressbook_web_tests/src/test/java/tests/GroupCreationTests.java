package tests;

import model.GroupData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.List;

public class GroupCreationTests extends TestBase {

    public static List<GroupData> groupProvider() {
        var result = new ArrayList<>(List.of(
                new GroupData("", randomStringWithNumbers(1000), randomStringWithNumbers(1000), randomStringWithNumbers(1000)),
                new GroupData("", "name"+randomStringWithNumbers(1000),
                        "header"+randomStringWithNumbers(1000),
                        "footer"+randomStringWithNumbers(1000)),
                new GroupData("", "name .,/-+;:?\"@#!$%^&*()_=", "header .,/-+;:?\"@#!$%^&*()_=",
                        "footer .,/-+;:?\"@#!$%^&*()_=")
        ));
        for (var name : List.of("", "group name")) {
            for (var header : List.of("", "group header")) {
                for (var footer : List.of("", "group footer")) {
                    result.add(new GroupData().withName(name).withHeader(header).withFooter(footer));
                }
            }
        }
        for (int i = 1; i < 5; i++) {
            result.add(new GroupData()
                    .withName(randomString(i * 10))
                    .withHeader(randomString(i * 10))
                    .withFooter(randomString(i * 10)));
        }
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
        expectedGroups.add(group.withId(newGroups.get(newGroups.size()-1).id()).withHeader("").withFooter(""));
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
