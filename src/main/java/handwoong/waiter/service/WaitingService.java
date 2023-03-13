package handwoong.waiter.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import handwoong.waiter.domain.Member;
import handwoong.waiter.domain.Waiting;
import handwoong.waiter.domain.WaitingStatus;
import handwoong.waiter.form.WaitingForm;
import handwoong.waiter.repository.MemberRepository;
import handwoong.waiter.repository.WaitingRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import sens.MessageService;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class WaitingService {

	private final WaitingRepository waitingRepository;
	private final MemberRepository memberRepository;
	private final MessageService messageService;

	public Waiting findOne(UUID waitingId) {
		return waitingRepository.findOne(waitingId);
	}

	public List<Waiting> findWaitingList(UUID memberId) {
		return waitingRepository.findAll(memberId);
	}

	@Transactional
	public Waiting register(UUID memberId, WaitingForm waitingForm) {
		int waitingNumber = 1;
		int waitingTurn = 0;

		Member member = memberRepository.findOne(memberId);
		List<Waiting> waitingList = member.getWaitingList();

		if (!waitingList.isEmpty()) {
			Waiting latestWaiting = waitingList.get(waitingList.size() - 1);
			waitingNumber = latestWaiting.getWaitingNumber() + 1;
			waitingTurn = latestWaiting.getWaitingTurn() + 1;
		}

		Waiting waiting = Waiting.createWaiting(
			member, waitingNumber, waitingTurn,
			waitingForm.getAdult(), waitingForm.getChildren(), waitingForm.getPhoneNumber());

		waitingRepository.save(waiting);
		member.addWaiting(waiting);
		// TODO 알림톡
		return waiting;
	}

	@Transactional
	public void deleteWaiting(UUID waitingId) {
		Waiting waiting = waitingRepository.findOne(waitingId);
		waiting.cancel(WaitingStatus.ENTER);
	}

	@Transactional
	public void cancelWaiting(UUID waitingId) {
		Waiting waiting = waitingRepository.findOne(waitingId);
		waiting.cancel(WaitingStatus.CANCEL);
		// TODO 알림톡
	}

	public void notice(UUID memberId, UUID waitingId) {
		Waiting waiting = waitingRepository.findOne(waitingId);
		// TODO 알림톡
		readyNotice(memberId, waitingId);
	}

	private void readyNotice(UUID memberId, UUID waitingId) {
		List<Waiting> waitingList = waitingRepository.findAll(memberId);
		if (waitingList.size() < 3)
			return;

		boolean isFirstWaiting = waitingList.get(0).getId().equals(waitingId);
		if (!isFirstWaiting)
			return;

		Waiting thirdWaiting = waitingList.get(2);
		if (thirdWaiting.isSendMessage())
			return;

		// TODO 알림톡
		thirdWaiting.changeSendStatus();
	}
}
