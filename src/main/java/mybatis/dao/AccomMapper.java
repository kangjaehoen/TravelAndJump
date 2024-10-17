package mybatis.dao;

import com.example.tnj.domain.AccVO;
import com.example.tnj.domain.CategoryVO;
import com.example.tnj.domain.PayVO;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface AccomMapper {
    @Select("select * from accom where id = #{id}")
    public List<AccVO> listmine(String id);
    //등록
    @Insert("INSERT INTO accom " +
            " (accomNum, id, accName, postcode, address, detailAddress,  price, accCall, adultPrice, " +
            " kidPrice, dayoff, category, accType, onSale, accomRule," +
            " informtext, ocupacy, maximumOcupacy) " +
            " VALUES (#{accomNum}, #{id}, #{accName}, #{postcode}, #{address}, #{price}," +
            " #{accCall}, #{adultPrice}, #{kidPrice}, #{dayoff}, #{category}, #{accType}, " +
            " #{accPic}, #{onSale}, #{accomRule}, #{informtext}, #{ocupacy}, #{maximumOcupacy} " +
            " #{chkin_Time}, #{chkout_Time}), #{room}, #{bed}, #{bathroom}")
    public boolean accinsert(AccVO ap);

    //삭제
    @Delete("delete accom where id=#{id} and accomNum =#{accomNum}")
    public int accdelete(String id, int accomNum);
    //결제확인
    @Select("select * from pay where accomNum in (select accomNum from accom where id = #{id})")
    public List<PayVO>reservationcheck(String id);
    //불러오기
    @Select("select * from accom where id =#{id} and accomNum=#{accomNum}")
    public AccVO oneacc(String id, int accomNum);

    @Select(" SELECT a.accomNum, a.id, a.accName, a.postcode, a.address, " +
            "        a.detailAddress, a.accCall, a.adultPrice, a.kidPrice, " +
            "        a.dayoff, a.category, a.accType, a.onSale , " +
            "        a.accomRule, a.occ, a.maxOcc, FORMAT(a.price,0) as price, " +
            "        a.informtext, a.chkin_Time, a.chkout_Time, a.room, " +
            "        a.bed, a.bathroom, round(avg(r.satisfy),1) as satisAvg " +
            " FROM accom a " +
            " LEFT JOIN review r " +
            " ON  a.accomNum = r.accomNum" +
            " WHERE category = #{category} " +
            " GROUP BY a.accomNum ")
    public List<CategoryVO> categoryAccomList(String category);


    @Select(" SELECT a.accomNum, a.id, a.accName, a.postcode, a.address, " +
            "        a.detailAddress, a.accCall, a.adultPrice, a.kidPrice, " +
            "        a.dayoff, a.category, a.accType,  a.onSale , " +
            "        a.accomRule, a.occ, a.maxOcc, FORMAT(a.price,0) as price, " +
            "        a.informtext, a.chkin_Time, a.chkout_Time, a.room, " +
            "        a.bed, a.bathroom, round(avg(r.satisfy),1) as satisAvg " +
            " FROM accom a " +
            " LEFT JOIN review r " +
            " ON  a.accomNum = r.accomNum" +
            " WHERE a.accType = #{accType} " +
            " GROUP BY a.accomNum ")
    public List<CategoryVO> accTypeAccomList(String accType);

    @Select(" SELECT a.accomNum, a.id, a.accName, a.postcode, a.address, " +
            "        a.detailAddress, a.accCall, a.adultPrice, a.kidPrice, " +
            "        a.dayoff, a.category, a.accType, a.onSale , " +
            "        a.accomRule, a.occ, a.maxOcc, FORMAT(a.price,0) as price, " +
            "        a.informtext, a.chkin_Time, a.chkout_Time, a.room, " +
            "        a.bed, a.bathroom, round(avg(r.satisfy),1) as satisAvg " +
            " FROM accom a " +
            " LEFT JOIN review r " +
            " ON a.accomNum = r.accomNum " +
            " WHERE (a.accName LIKE CONCAT('%', #{search}, '%') " +
            " OR a.address LIKE CONCAT('%', #{search}, '%') " +
            " OR a.accType LIKE CONCAT('%', #{search}, '%') " +
            " OR a.category LIKE CONCAT('%', #{search}, '%')) " +
            " GROUP BY a.accomNum ")
    public List<CategoryVO> searchAccomList(String search);

}


