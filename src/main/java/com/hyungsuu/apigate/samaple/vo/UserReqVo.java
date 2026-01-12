package com.hyungsuu.apigate.samaple.vo;

import java.io.Serializable;
import java.sql.Date;

import com.hyungsuu.common.vo.BaseResponseVo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;


@SuppressWarnings("serial")
@Data
@Schema(description = "샘플 조회 API")
public class UserReqVo extends UserBaseVo implements Serializable {
	

	@NotNull(message = "사용자권한은은 Null 일 수 없습니다.")
	@Size(max = 10, message = "사용자 권한는 10자를 넘을수 없습니다.")
	@Schema(description = "사용자 권한",example = "user", requiredMode = Schema.RequiredMode.REQUIRED)
	private String userAuth;
	
	
	@NotNull(message = "사용자이름은 Null 일 수 없습니다.")
	@Size(min = 2, max = 20, message = "사용자 이름는 20자를 넘을수 없습니다.")
	@Schema(description = "사용자 권한",example = "user", requiredMode = Schema.RequiredMode.REQUIRED)
	private String userName;
	
	
	@Schema(description = "사용자 권한",example = "user")
	private Date regDate;
	

}
