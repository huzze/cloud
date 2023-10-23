package com.ftp.dto;

import com.ftp.domain.ChunkFile;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.web.multipart.MultipartFile;

/**
 * @program: pms
 * @description: 文件分块dto
 * @author: linky
 * @create: 2020-08-27 19:59
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class ChunkFileDto extends ChunkFile {
    /**
     * 上传的文件对象
     */
    private MultipartFile file;
}
