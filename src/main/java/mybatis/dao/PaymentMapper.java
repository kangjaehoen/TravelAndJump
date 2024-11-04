package mybatis.dao;

import com.example.tnj.domain.AccVO;
import com.example.tnj.domain.PayResAccomVO;
import com.example.tnj.domain.PayVO;
import com.example.tnj.domain.PaymentDTO;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface PaymentMapper {
    //예약테이블에 생성된 resNum 가져오기
    @Select("select resNum from reservation where accomNum=#{accomNum} ORDER BY resNum DESC LIMIT 1")
    public int selectresNum(@Param("accomNum") int accomNum);

    //결제테이블 insert
    @Insert("insert into pay( resNum, id, payDate, pay_Status, accomNum, amount, impUid, merchantUid, name, apply_num)  values( #{resNum},#{id}, now(), 'Y', #{accomNum}, #{amount}, #{impUid}, #{merchantUid}, #{name}, #{apply_num})")
    public void insertPay(PaymentDTO pDTO);

    //결제내역
    @Select("select * from pay where accomNum= #{accomNum}")
    public PayVO payInfo(@Param("accomNum") int accomNum);

    //결제취소
    @Update("update pay set pay_Status='N' where impUid=#{impUid}")
    public int cancelPay(@Param("impUid") String impUid);

    //resNum에 해당하는 모든 결제정보
    @Select("select * from pay where resNum=#{resNum}")
    public List<PaymentDTO> listAll(@Param("resNum") int resNum);

    //세션에 따른 리스트출력
    @Select("select payDate,impUid,name,amount,pay_Status from pay where id=#{id}")
    public List<PaymentDTO> payList(@Param("id") String id);

    //결제상태에 따른 조회
    @Select("select * from pay where pay_Status=#{pay_Status}")
    public List<PaymentDTO> statusByList(@Param("pay_Status") String payStatus,@Param("resNum") int resNum);




    @Select(" SELECT  p.payDate AS payDate, p.accomNum AS accomNum, " +
            "         p.amount AS amount, p.impUid AS impUid, " +
            "         r.chkin_Date AS chkin_Date, r.chkout_Date AS chkout_Date, " +
            "         r.adultCnt AS adultCnt, r.kidCnt AS kidCnt, p.id AS id , r.resNum AS resNum , " +
            "         a.accName AS accName, a.postCode AS postCode, " +
            "         a.address AS address, a.detailAddress AS detailAddress, " +
            "         a.accCall AS accCall, a.price AS price, a.chkin_Time AS chkin_Time, " +
            "         a.chkout_Time AS chkout_Time, " +
            "         ai.filePath AS filePath, COUNT(*) AS reviewCount, " +
            "         round(avg(satisfy),1) as satisAvg , DATEDIFF( r.chkout_Date, r.chkin_Date) AS totalDay " +
            " FROM pay p " +
            " JOIN reservation r" +
            " ON p.resNum = r.resNum" +
            " JOIN accom a" +
            " ON a.accomNum = r.accomNum" +
            " LEFT JOIN accomimage ai" +
            " ON a.accomNum = ai.accomNum " +
            " LEFT JOIN  review rv " +
            " ON a.accomNum = rv.accomNum "+
            " WHERE p.impUid = #{impUid} ")
    public PayResAccomVO payReservAccomInfo(String impUid);

}
