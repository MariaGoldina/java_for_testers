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
                new GroupData("12345", "12345", "12345"),
                new GroupData("group name1", "header2", "footer3"),
                new GroupData("group name.,/-+;:?\"@#!$%^&*()_=", "header.,/-+;:?\"@#!$%^&*()_=",
                        "footer.,/-+;:?\"@#!$%^&*()_=")
        ));
        for (var name : List.of("", "group name")) {
            for (var header : List.of("", "group header")) {
                for (var footer : List.of("", "group footer")) {
                    result.add(new GroupData(name, header, footer));
                }
            }
        }
        for (int i = 1; i < 5; i++) {
            result.add(new GroupData(randomString(i * 10), randomString(i * 10), randomString(i * 10)));
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
        int groupCount = app.groups().getGroupsCount();
        app.groups().createGroup(group);
        int newGroupCount = app.groups().getGroupsCount();
        Assertions.assertEquals(groupCount + 1, newGroupCount);
    }

    @ParameterizedTest
    @MethodSource("negativeGroupProvider")
    public void cannotCreateGroup(GroupData group) {
        int groupCount = app.groups().getGroupsCount();
        app.groups().createGroup(group);
        int newGroupCount = app.groups().getGroupsCount();
        Assertions.assertEquals(groupCount, newGroupCount);
    }
}
