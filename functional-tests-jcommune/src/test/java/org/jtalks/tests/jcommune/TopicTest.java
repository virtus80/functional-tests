/**
 * Copyright (C) 2011  JTalks.org Team
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
 */

package org.jtalks.tests.jcommune;

import org.jtalks.tests.jcommune.utils.StringHelp;
import org.jtalks.tests.jcommune.webdriver.action.Topics;
import org.jtalks.tests.jcommune.webdriver.action.Users;
import org.jtalks.tests.jcommune.webdriver.entity.topic.Poll;
import org.jtalks.tests.jcommune.webdriver.entity.topic.Topic;
import org.jtalks.tests.jcommune.webdriver.entity.user.User;
import org.jtalks.tests.jcommune.webdriver.entity.user.UserForRegistration;
import org.jtalks.tests.jcommune.webdriver.exceptions.PermissionsDeniedException;
import org.jtalks.tests.jcommune.webdriver.exceptions.ValidationException;
import org.jtalks.tests.jcommune.webdriver.page.MainPage;
import org.testng.Assert;
import org.testng.annotations.*;

import java.util.Date;

import static org.jtalks.tests.jcommune.webdriver.JCommuneSeleniumConfig.driver;
import static org.jtalks.tests.jcommune.webdriver.page.Pages.mainPage;
import static org.jtalks.tests.jcommune.webdriver.page.Pages.topicPage;

import org.jtalks.tests.jcommune.webdriver.page.TopicPage;

/**
 * @author Guram Savinov
 */
public class TopicTest {

    @BeforeMethod
    @Parameters({"appUrl"})
    public void setupCase(String appUrl) throws ValidationException {
        driver.get(appUrl);
        mainPage.logOutIfLoggedIn(driver);
    }

    @Test
    public void createTopicWithTitleAndMessage_JC_13() throws Exception {
        User user = Users.signUp();
        Users.signIn(user);
        Topic topic = new Topic("subject", "message");
        Topic createdTopic = Topics.createTopic(topic);
        Assert.assertTrue(Topics.isCreated(createdTopic));
    }

    @Test(expectedExceptions = ValidationException.class, expectedExceptionsMessageRegExp = TopicPage.EMPTY_SUBJECT_ERROR)
    public void createTopicWithEmptyTitleShouldFail_JC_25() throws Exception {
        User user = Users.signUp();
        Users.signIn(user);
        Topic topic = new Topic("", "message");
        Topics.createTopic(topic);
    }

    @Test(enabled = false, expectedExceptions = ValidationException.class, expectedExceptionsMessageRegExp = TopicPage.EMPTY_BODY_ERROR)
    public void createTopicWithEmptyMessageShouldFail_JC_26() throws Exception {
        User user = Users.signUp();
        Users.signIn(user);
        Topic topic = new Topic("subject", "");
        Topics.createTopic(topic);

    }

    @Test(enabled = false, expectedExceptions = ValidationException.class, expectedExceptionsMessageRegExp = TopicPage.EMPTY_SUBJECT_ERROR + TopicPage.EMPTY_BODY_ERROR)
    public void createTopicWithoutData_JC_24() throws Exception {
        User user = Users.signUp();
        Users.signIn(user);
        Topic topic = new Topic("", "");
        Topics.createTopic(topic);

    }

    @Test(enabled = false)
    public void LoginAndCreateTopic() throws Exception {
        Topic topic = new Topic("", "message");
        Topics.loginAndCreateTopic(topic);
        Topics.isBranch(topic);
    }

    @Test(enabled = true)
    public void LoginAndCreateTopicValidateBranch() throws Exception {
        Topic topic = new Topic("subject123", "message").withBranch("TestBranch");
        Topics.loginAndCreateTopic(topic);
        Assert.assertEquals(true, Topics.isInCorrectBranch(topic));
    }

    @Test
    public void createTopicInBranch() throws ValidationException, PermissionsDeniedException {
        User user = Users.signUp();
        Users.signIn(user);
        Topic topic = new Topic("subject123", "message").withBranch("TestBranch2");
        topic.withTopicStarter(user);//?
        Topics.createTopic(topic);
    }

    @Test
    public void createCodeReviewInBranch() throws Exception {
        Topic topic = new Topic("test_code_review1", "SomeCode").withBranch("TestBranch");
        User user = Users.signUp();
        Users.signIn(user);
        topic.withTopicStarter(user);
        Topics.createCodeReview(topic);

    }

    @Test
    public void postValidAnswerToTopicShouldSucceed() throws Exception {
        //In this test title of topic variable means subject of post we want to add answer to, and the answer, actually
        Topic topic = new Topic(StringHelp.randomString(40), StringHelp.randomString(100));
        String branchTitle = "TestBranch";
        User user = Users.signUp();
        Users.signIn(user);
        topic.withTopicStarter(user);
        Topics.createTopic(topic);
        Topics.postAnswer(topic, branchTitle);
    }

    @Test(expectedExceptions = PermissionsDeniedException.class)
    public void createTopicAsAnonymousShouldFail() throws Exception {
        Topic topic = new Topic("subject", "message");
        Topics.createTopic(topic);
    }

    @Test
    public void createStickedTopic() throws Exception {
        Topic topic = new Topic("subject", "message");
        topic.setSticked(true);
        Topics.signUpAndCreateTopic(topic);
    }

    @Test
    public void createAnnouncementTopic() throws Exception {
        Topic topic = new Topic("subject", "message");
        topic.setAnnouncement(true);
        Topics.signUpAndCreateTopic(topic);
    }

    @Test
    public void createTopicWithPoll() throws Exception {
        Topic topic = new Topic("subject", "message");
        Poll poll = new Poll("poll title", new String[]{"option1", "option2", "option3"});
        topic.setPoll(poll);
        Topics.signUpAndCreateTopic(topic);
    }

    @Test
    public void createTopicWithPollEndDate() throws Exception {
        Topic topic = new Topic("subject", "message");
        Poll poll = new Poll("poll title", new String[]{"option1", "option2", "option3"});
        poll.setEndDate(new Date(System.currentTimeMillis() + 86400000));
        topic.setPoll(poll);
        Topics.signUpAndCreateTopic(topic);
    }

    @Test
    public void createTopicWithPollMultipleAnswers() throws Exception {
        Topic topic = new Topic("subject", "message");
        Poll poll = new Poll("poll title", new String[]{"option1", "option2", "option3"});
        poll.setMultipleAnswers(true);
        topic.setPoll(poll);
        Topics.signUpAndCreateTopic(topic);
    }

}
