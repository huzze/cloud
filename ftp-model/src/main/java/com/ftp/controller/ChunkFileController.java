package com.ftp.controller;

import com.ftp.domain.ChunkFile;
import com.ftp.domain.FileBase;
import com.ftp.dto.ChunkFileDto;
import com.ftp.service.ChunkFileService;
import com.ftp.template.FtpTemplate;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.UUID;

/**
 * @program: cloud
 * @description: 分片上传
 * @author: 林开颜
 * @create: 2023/10/23 9:28
 */
@Slf4j
@RestController
@RequestMapping("/v0.1/file/chunk")
public class ChunkFileController {
    @Value("${prop.upload-folder}")
    private String uploadFolder;
    @Value("${ftp.nginx}")
    private String FTP_NGINX;
    @Resource
    private ChunkFileService chunkFileService;
    @Resource
    private FtpTemplate ftpTemplate;

    @PostMapping("/chunk")
    public void add (@RequestBody ChunkFileDto chunkFile) {
        MultipartFile file = chunkFile.getFile();
        try {
            byte[] bytes = file.getBytes();
            String s = generatePath(uploadFolder, chunkFile);
            chunkFile.setRelativePath(s);
            Path path = Paths.get(s);
            //文件写入指定路径
            Files.write(path, bytes);
            log.debug("文件 {} 写入成功, uuid:{}", chunkFile.getName(), chunkFile.getIdentifier());
            chunkFileService.add(chunkFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String generatePath(String uploadFolder, ChunkFileDto chunk) {
        StringBuilder sb = new StringBuilder();
        sb.append(uploadFolder).append("/").append(chunk.getIdentifier());
        //判断uploadFolder/identifier 路径是否存在，不存在则创建
        if (!Files.isWritable(Paths.get(sb.toString()))) {
            log.info("path not exist,create path: {}", sb.toString());
            try {
                Files.createDirectories(Paths.get(sb.toString()));
            } catch (IOException e) {
                log.error(e.getMessage(), e);
            }
        }

        return sb.append("/")
                .append(chunk.getName())
                .append("-")
                .append(chunk.getChunkNum()).toString();
    }

    @GetMapping("/verification/chunk")
    public Boolean checkChunk(@RequestParam String identifier, @RequestParam Integer chunkNum) {
        ChunkFile chunkFile = chunkFileService.getByIdentifierAndChunkNum(identifier, chunkNum);
        if (null == chunkFile) {
//            throw new Exception("CHUNK_FILE/CHUNK_NONE", "CHUNK_NONE", HttpStatus.BAD_REQUEST);
            return Boolean.FALSE;
        } else {
            return Boolean.TRUE;
        }
    }

    /**
     * description 文件合并上传
     * @param identifier
     * @param fileName
     * @return com.jw.pms.domain.FileBase
     * @author Linky
     * @date 2020/8/29 10:49
     */
    @PostMapping("/chunk/merge")
    public FileBase mergeFile(@RequestParam String identifier, @RequestParam String fileName) {
        String file = uploadFolder + "/" + identifier + "/" + fileName;
        String folder = uploadFolder + "/" + identifier;
        merge(file, folder, fileName);

        File toFile = new File(file);
        log.info("合并后文件：{}", toFile);
//        fileInfoService.addFileInfo(fileInfo);
        // 文件上传返回文件对象
        String prefixPath = FTP_NGINX;
        // MultipartFile 转 File
        // 保存到文件服务器
        String filePath = ftpTemplate.uploadFile(toFile);
        if (StringUtils.isEmpty(filePath)) {
//            throw new JafException("PMS/UPLOAD_FAIL", "上传失败", HttpStatus.BAD_REQUEST);
        }
        // 数据库保存路径
        prefixPath = prefixPath + filePath;
        FileBase fileBase = new FileBase();
        fileBase.setId(UUID.randomUUID().toString());
        fileBase.setName(fileName);
        fileBase.setUrl(prefixPath);
        fileBase.setMd5(identifier);
        return fileBase;
    }

    /**
     * 文件合并
     *
     * @param targetFile
     * @param folder
     */
    private void merge(String targetFile, String folder, String filename) {
        try {
            Files.createFile(Paths.get(targetFile));
            Files.list(Paths.get(folder))
                    .filter(path -> !path.getFileName().toString().equals(filename))
                    .sorted((o1, o2) -> {
                        String p1 = o1.getFileName().toString();
                        String p2 = o2.getFileName().toString();
                        int i1 = p1.lastIndexOf("-");
                        int i2 = p2.lastIndexOf("-");
                        return Integer.valueOf(p2.substring(i2)).compareTo(Integer.valueOf(p1.substring(i1)));
                    })
                    .forEach(path -> {
                        try {
                            //以追加的形式写入文件
                            Files.write(Paths.get(targetFile), Files.readAllBytes(path), StandardOpenOption.APPEND);
                            //合并后删除该块
                            Files.delete(path);
                        } catch (IOException e) {
                            log.error(e.getMessage(), e);
                        }
                    });
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }
}
