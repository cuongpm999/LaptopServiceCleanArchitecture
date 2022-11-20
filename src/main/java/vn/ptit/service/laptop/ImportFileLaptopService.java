package vn.ptit.service.laptop;

import lombok.SneakyThrows;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import vn.ptit.exception.DataNotFoundException;
import vn.ptit.model.Laptop;
import vn.ptit.model.Manufacturer;
import vn.ptit.repository.laptop.ILaptopRepository;
import vn.ptit.repository.manufacturer.IManufacturerRepository;

import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class ImportFileLaptopService {
    private final ILaptopRepository laptopRepository;
    private final IManufacturerRepository manufacturerRepository;

    public ImportFileLaptopService(ILaptopRepository laptopRepository, IManufacturerRepository manufacturerRepository) {
        this.laptopRepository = laptopRepository;
        this.manufacturerRepository = manufacturerRepository;
    }

    @SneakyThrows
    public void importExcel(InputStream inputStream) {
        List<Laptop> laptops = new ArrayList<>();

        XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
        XSSFSheet sheet = workbook.getSheetAt(0);

        for (int i = 1; i < sheet.getPhysicalNumberOfRows(); i++) {
            XSSFRow row = sheet.getRow(i);
            if (isEmptyRow(row)) continue;

            String name = row.getCell(0).getStringCellValue();
            Integer category = (int) row.getCell(1).getNumericCellValue();
            String hardDrive = row.getCell(2).getStringCellValue();
            String ram = row.getCell(3).getStringCellValue();
            String vga = row.getCell(4).getStringCellValue();
            String cpu = row.getCell(5).getStringCellValue();
            Double screen = row.getCell(6).getNumericCellValue();
            Double price = row.getCell(7).getNumericCellValue();
            Double discount = row.getCell(8).getNumericCellValue();
            String video = row.getCell(9).getStringCellValue();
            String specifications = row.getCell(10).getStringCellValue();
            int manufacturerId = (int) row.getCell(11).getNumericCellValue();
            List<String> images = Arrays.asList(row.getCell(12).getStringCellValue().split("\\,"));

            Laptop laptop = Laptop.create(name, category, hardDrive, ram, vga, cpu, screen, price, discount, video, specifications, images);

            Manufacturer manufacturer = manufacturerRepository.getById(manufacturerId);
            if (manufacturer == null) {
                throw new DataNotFoundException("Manufacturer not found");
            }
            laptop.setManufacturer(manufacturer);
            laptops.add(laptop);
        }

        laptops.parallelStream().forEach(laptopRepository::save);
    }

    private boolean isEmptyRow(XSSFRow row) {
        if (row == null) {
            return true;
        }
        if (row.getLastCellNum() <= 0) {
            return true;
        }
        for (int cellNum = row.getFirstCellNum(); cellNum < row.getLastCellNum(); cellNum++) {
            XSSFCell cell = row.getCell(cellNum);
            if (cell != null && cell.getCellType() != CellType.BLANK && StringUtils.isNotBlank(cell.toString())) {
                return false;
            }
        }
        return true;
    }
}
