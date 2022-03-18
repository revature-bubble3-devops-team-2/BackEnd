package com.revature.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.revature.models.Comment;
import com.revature.models.Group;
import com.revature.models.Notification;
import com.revature.models.Post;
import com.revature.models.Profile;

public class NotificationDTOTest {
    private static final int NID = 0;
    private static final boolean ISREAD = true;
	private static final Comment COMMENT = new Comment();
	private static final Profile OWNER = new Profile();
	private static final Profile PERSON2 = new Profile();
	private static final Post POST = new Post();
	private static NotificationDTO notificationDto1;
	private static NotificationDTO notificationDto2;
	private static Notification notification;



	@BeforeEach
	void init() {
		notificationDto1 = new NotificationDTO(NID,ISREAD,COMMENT,OWNER,PERSON2,POST);
		notificationDto2 = new NotificationDTO(NID,ISREAD,COMMENT,OWNER,PERSON2,POST);
        notification = new Notification(NID,ISREAD,COMMENT,OWNER,PERSON2,POST);
	}

	@Test
	void testGetters() {
		assertEquals(NID, notificationDto1.getNid());
		assertEquals(COMMENT, notificationDto1.getCid());
		assertEquals(OWNER, notificationDto1.getFromProfileId());
		assertEquals(PERSON2, notificationDto1.getToProfileId());
		assertEquals(POST, notificationDto1.getPostId());

	}

	@Test
	void testSetters() {
		NotificationDTO notificationDto = new NotificationDTO();
		notificationDto.setNid(NID);
        notificationDto.setRead(ISREAD);
		notificationDto.setCid(COMMENT);
		notificationDto.setFromProfileId(OWNER);
		notificationDto.setToProfileId(PERSON2);
		notificationDto.setPostId(POST);

		assertEquals(notificationDto, notificationDto1);
	}

	@Test
	void testEquals() {
		assertEquals(notificationDto1, notificationDto2);
	}

	@Test
	void testHashCode() {
		assertEquals(notificationDto1.hashCode(), notificationDto2.hashCode());
	}

	@Test
	void testModelConstructor() {
		//The constructor changes the unique id models -> Notification -> Constructors
		NotificationDTO result = new NotificationDTO(notification);
		result.setNid(notificationDto1.getNid());
		assertEquals(notificationDto1, result);
	}

	@Test
	void testToGroup() { 
		//The constructor changes the unique id models -> Notification -> Constructors
		Notification result = notificationDto1.toNotification();	
		result.setNid(notification.getNid());
		assertEquals(notification, result);
	}

	@Test
	void testCustomConstructor() {
		NotificationDTO nDto = new NotificationDTO(5,ISREAD,COMMENT,OWNER,PERSON2,POST);
		assertNotEquals(notificationDto1.getNid(), nDto.getNid());
		nDto.setNid(notificationDto1.getNid());
		assertEquals(notificationDto1, nDto);
	}
}
