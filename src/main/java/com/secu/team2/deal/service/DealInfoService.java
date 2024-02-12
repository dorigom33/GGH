package com.secu.team2.deal.service;

import java.util.List;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.secu.team2.chat.service.ChatInfoService;
import com.secu.team2.chat.vo.ChatInfoVO;
import com.secu.team2.deal.mapper.DealInfoMapper;
import com.secu.team2.deal.vo.DealInfoRecordVO;
import com.secu.team2.deal.vo.DealInfoVO;
import com.secu.team2.file.mapper.FileInfoMapper;
import com.secu.team2.rank.mapper.RankInfoMapper;
import com.secu.team2.rank.vo.RankInfoVO;
import com.secu.team2.user.vo.UserInfoVO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class DealInfoService {

	private final DealInfoMapper dealInfoMapper; // 생성자 주입
	private final ChatInfoService chatInfoService; // 생성자 주입
	private final FileInfoMapper fileInfoMapper;
	private final RankInfoMapper rankInfoMapper;

	public DealInfoVO selectDealInfoBySeller() { // 판매자 번호로 거래정보 조회
		int sellerUiNum = getCurrentUserUiNum();
		return dealInfoMapper.SelectDealInfoBySeller(sellerUiNum);
	}

	public DealInfoVO selectDealInfoByBuyer() { // 구매자 번호로 거래정보 조회
		int buyerUiNum = getCurrentUserUiNum();
		return dealInfoMapper.SelectDealInfoByBuyer(buyerUiNum);

	}
	
	public DealInfoVO selectBiNumByDiNum(int diNum) {
		return dealInfoMapper.selectBiNumByDiNum(diNum);
	}

	public int countDealInfoByDiStat(int buyerUiNum) {
		return dealInfoMapper.countDealInfoByDiStat(buyerUiNum);
	}

	private int getCurrentUserUiNum() { // 현재 사용자 번호 가져오는 메서드
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (principal instanceof UserDetails) {
			return ((UserInfoVO) principal).getUiNum();
		} else {
			return -1;
		}
	}

	public int insertDealInfoWithChatInfo(int ciNum) { // 거래정보 입력
		ChatInfoVO chatInfo = chatInfoService.selectChatInfoByCiNum(ciNum); // 채팅에서 biNum, sellerUiNum, buyerUiNum을 추출
		int biNum = chatInfo.getBiNum();
		int sellerUiNum = chatInfo.getSellerUiNum();
		int buyerUiNum = chatInfo.getBuyerUiNum();
		DealInfoVO deal = new DealInfoVO(); // deal 변수에 추출한 내용 저장
		deal.setBiNum(biNum);
		deal.setBuyerUiNum(buyerUiNum);
		deal.setSellerUiNum(sellerUiNum);

		dealInfoMapper.insertDealInfoWithChatInfo(deal); // 거래정보 입력

		int generatedDiNum = deal.getDiNum(); // 거래정보 입력시 생성된 diNum을 generatedDiNum 변수에 저장

		return generatedDiNum; // 새로 생성된 diNum 반환. order입력시 사용됨
	}

	public int updateBuyerDiStat(int diNum) {
		return dealInfoMapper.updateBuyerDiStat(diNum);
	}
	
	public List<DealInfoRecordVO> getDealInfoListByUiNum(int uiNum){
		List<DealInfoRecordVO> dealList = dealInfoMapper.getDealInfoListByUiNum(uiNum);
		for(DealInfoRecordVO deal : dealList) {
			DealInfoVO info = new DealInfoVO();
			info.setBuyerUiNum(uiNum);
			info.setDiNum(deal.getDiNum());
			RankInfoVO rank = rankInfoMapper.selectRankInfoByDiNumAndUiNum(info);
			log.info("rankInfo=>{}",rank);
			if(rank == null) {
				deal.setRiComment(null);
				deal.setRiRank(null);
			}else {
				deal.setRiComment(rank.getRiComment());
				deal.setRiRank(rank.getRiRank());
			}
			deal.setFiles(fileInfoMapper.selectFileInfos(deal.getBiNum()));
		}
		return dealList;
	}
}
