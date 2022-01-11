package cn.edu.tyut.sea2.seandisk.module.disk.component;

import cn.edu.tyut.sea2.seandisk.common.exception.GeneralException;
import cn.edu.tyut.sea2.seandisk.common.utils.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.UUID;

/**
 * 本地文件操作组件
 * @author KlenKiven
 */
@Component
public class LocalFileOperation {

    @Value("${sean.local-file.location}")
    private String localFileLocation;

    /**
     * 保存一个文件并且返回文件的SHA-1散列值
     * <p>
     *     首先将文件保存为临时文件，然后计算文件的SHA-1值，
     *     通过计算的SHA-1值确定文件的名称，最后保存文件，删除临时文件
     * </p>
     * @param multipartFile 传来的文件
     * @return 物理文件的散列值
     */
    public String writeFile(MultipartFile multipartFile) {
        FileInputStream fis;
        String sha1 = "";
        String randomUUID = UUID.randomUUID().toString().replace("-", "");
        try {
            File tempFile = new File(localFileLocation + "temp-" + randomUUID);
            multipartFile.transferTo(tempFile);
            fis = new FileInputStream(tempFile);
            sha1 = FileUtils.fileSHA1Compute(fis);
            fis.close();

            fis = new FileInputStream(tempFile);
            File physicalFile = new File(localFileLocation + sha1);
            FileOutputStream fos = new FileOutputStream(physicalFile);
            FileUtils.inputToOutputStream(fis, fos);
            if (!tempFile.delete()) {
                throw new GeneralException("临时文件" + tempFile.getName() + "未被正常删除");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sha1;
    }

    public InputStream getFileInputStream(String fileId) throws FileNotFoundException {
        String fileLocation = localFileLocation + fileId;
        return new FileInputStream(fileLocation);
    }
}
