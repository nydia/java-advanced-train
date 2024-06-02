package com.nydia.dddmessage.application.impl;

import com.google.common.eventbus.Subscribe;
import com.nydia.dddmessage.application.NoticeCommandService;
import com.nydia.dddmessage.domain.aggregate.message.event.MessageCreatedEvent;
import com.nydia.dddmessage.domain.aggregate.notice.AppMessagePublisher;
import com.nydia.dddmessage.domain.aggregate.notice.SocketMessagePublisher;
import com.nydia.dddmessage.domain.aggregate.notice.entity.Notice;
import com.nydia.dddmessage.domain.aggregate.notice.repository.NoticeRepository;
import com.nydia.dddmessage.domain.shared.event.DomainEventPublisher;
import org.springframework.stereotype.Service;

/**
 * @author nydia
 * Created on 2021/7/23
 */
@Service
public class NoticeServiceImpl implements NoticeCommandService {
    private final AppMessagePublisher appMessagePublisher;
    private final SocketMessagePublisher socketMessagePublisher;
    private final NoticeRepository noticeRepository;

    public NoticeServiceImpl(
            DomainEventPublisher domainEventPublisher,
            AppMessagePublisher appMessagePublisher,
            SocketMessagePublisher socketMessagePublisher,
            NoticeRepository noticeRepository
    ) {
        this.appMessagePublisher = appMessagePublisher;
        this.socketMessagePublisher = socketMessagePublisher;
        this.noticeRepository = noticeRepository;
        domainEventPublisher.register(this);
    }

    @Override
    @Subscribe
    public void createNotice(MessageCreatedEvent messageCreatedEvent) {
        Notice notice = new Notice(messageCreatedEvent.getData());
        notice.createImMessage()
                .forEach(socketMessagePublisher::publish);
        notice.createAppMessages()
                .forEach(appMessagePublisher::publish);
        noticeRepository.save(notice);
    }
}
