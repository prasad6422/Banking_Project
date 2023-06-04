package com.web.service;

import java.awt.Color;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.web.excelhelper.ExcelHelper;
import com.web.model.DepositModel;
import com.web.model.Details;
import com.web.model.FileEntity;
import com.web.model.Transaction;
import com.web.model.WithdrawModel;
import com.web.repo.DetailsRepo;
import com.web.repo.EntityRepository;
import com.web.repo.TransactionRepo;
@Service
public class DetailsServiceImp implements DetailsService {
	@Autowired
	private DetailsRepo drepo;
	
	@Autowired
	private TransactionRepo trepo;
	
	@Autowired
	private EntityRepository entityrepo;
	
	@Override
	public Details saveDetails(Details details) {
		details.setFullname(details.getFirstname()+" "+details.getLastname());
		details.setStatus("active");
		
		if(details.getBranch().equalsIgnoreCase("Hyderabad")) {
			details.setIfsccode("HDFC0001");
		}
		else if(details.getBranch().equals("Bangalore")) {
			details.setIfsccode("HDFC0002");
		}
		else{
			details.setIfsccode("HDFC0003");
		}
		Details d=drepo.save(details);
		Transaction t=new Transaction();
		t.setAccountnumber(details.getAccountnumber());
		t.setAddress(details.getAddress());
		t.setCredit(details.getCurrentbalance());
		t.setDebit(0.0);
		t.setCurrentbalance(details.getCurrentbalance());
		t.setFullname(details.getFirstname()+details.getLastname());
		LocalDateTime date = LocalDateTime.now();
		t.setDate(date);
	    trepo.save(t);
			return d;
	}

	@Override
	public Transaction deposit(DepositModel details) {
		
		Details oldDets=drepo.findById(details.getAccountnumber()).get();
		oldDets.setCurrentbalance(details.getDepositeAmount()+oldDets.getCurrentbalance());
		drepo.save(oldDets);
		Transaction t=new Transaction();
		t.setAccountnumber(details.getAccountnumber());
		t.setAddress(oldDets.getAddress());
		t.setCredit(details.getDepositeAmount());
		t.setDebit(0.0);
		t.setCurrentbalance(oldDets.getCurrentbalance());
		t.setFullname(oldDets.getFirstname()+oldDets.getLastname());

		LocalDateTime date = LocalDateTime.now();
		t.setDate(date);
	      trepo.save(t);
		return t;
	}
	@Override
	public Transaction withdraw(WithdrawModel details) {
		Details oldDets=drepo.findById(details.getAccountnumber()).get();
		if(oldDets.getCurrentbalance()>=details.getWithdrawAmount()) {
		oldDets.setCurrentbalance(oldDets.getCurrentbalance()-details.getWithdrawAmount());
		drepo.save(oldDets);
		Transaction t=new Transaction();
		t.setAccountnumber(details.getAccountnumber());
		t.setAddress(oldDets.getAddress());
		t.setDebit(details.getWithdrawAmount());
		t.setCredit(0.0);
		t.setCurrentbalance(oldDets.getCurrentbalance());
		t.setFullname(oldDets.getFirstname()+oldDets.getLastname());
		
		LocalDateTime date = LocalDateTime.now();
		t.setDate(date);
	      trepo.save(t);
	      return t;
		}
		else {
			
		return null;
		}
	}

	@Override
	public Transaction transfer(Details details, Integer id) {
		Details creditoldDets=drepo.findById(details.getAccountnumber()).get();
		Details debitoldDets=drepo.findById(id).get();
		if(debitoldDets.getCurrentbalance()>=details.getCurrentbalance()) {
		debitoldDets.setCurrentbalance(debitoldDets.getCurrentbalance()-details.getCurrentbalance());
		drepo.save(debitoldDets);
		creditoldDets.setCurrentbalance(details.getCurrentbalance()+creditoldDets.getCurrentbalance());
		drepo.save(creditoldDets);
		Transaction t=new Transaction();
		   t.setAccountnumber(id);
		   t.setAddress(debitoldDets.getAddress());
		   t.setDebit(details.getCurrentbalance());
		   t.setCredit(0.0);
		   t.setCurrentbalance(debitoldDets.getCurrentbalance());
		   t.setFullname(debitoldDets.getFirstname()+debitoldDets.getLastname());
		   LocalDateTime date = LocalDateTime.now();
			t.setDate(date);
	       trepo.save(t);
	    Transaction t1=new Transaction();
		   t1.setAccountnumber(details.getAccountnumber());
		   t1.setAddress(debitoldDets.getAddress());
		   t1.setCredit(details.getCurrentbalance());
		   t1.setDebit(0.0);
		   t1.setCurrentbalance(creditoldDets.getCurrentbalance());
		   t1.setFullname(debitoldDets.getFirstname()+debitoldDets.getLastname());
		   LocalDateTime date2 = LocalDateTime.now();
			t1.setDate(date2);
		   trepo.save(t1);
		
		return t1;
		}
		else {
			return null;
		}
		}
	
	@Override
	public Optional<Details> getOneDetail(Integer id) {
		return drepo.findById(id);
	}

	
	@Override
	public String deleteByAccountNumber(Integer id) {
		Optional<Details> detailsFound = getOneDetail(id);
		if(detailsFound.isPresent()) {
			drepo.deleteById(id);
			return "User Details Deleted Sucessfully";
		}else {
			return "No User Found";
		}
		
	}


	@Override
	public List<Details> getAllDetails() {
		List<Details> list=drepo.findAll();
		return list;
	}

	@Override
	public List<Transaction> findTop10ByOrderByDateDesc(PageRequest pageRequest,Integer accountnumber) {
		 return trepo.findTop10ByOrderByDateDesc(PageRequest.of(0, 10),accountnumber);
	}

	@Override
	public List<Transaction> findTop7ByOrderByDateDesc(PageRequest pageRequest, Integer accountnumber) {
		 return trepo.findTop7ByOrderByDateDesc(PageRequest.of(0, 7),accountnumber);
	}

//	@Override
//	public List<Transaction> findTop1ByOrderByDateDesc(Integer accountnumber) {
//		return trepo.findTop1ByOrderByDateDesc(accountnumber);
//	}

	@Override
	public List<Transaction> findTop1MonthByOrderByDateDesc(PageRequest pageRequest, Integer accountnumber) {
		
		return trepo.findTop1MonthByOrderByDateDesc(PageRequest.of(0, 30), accountnumber);
	}
	
	@Override
	@Transactional
	public List<Transaction> findByDateRangeByAccNumberDesc(Integer accountnumber, LocalDate fromdate,LocalDate todate) {
		LocalDateTime startDateTime = fromdate.atStartOfDay();
        LocalDateTime endDateTime = todate.plusDays(1).atStartOfDay().minusSeconds(1);
        return trepo.findByDateRangeByAccNumberDesc(accountnumber,startDateTime, endDateTime);
	}
	
	public ByteArrayInputStream load(Integer accountnumber, LocalDate fromdate,LocalDate todate) {
		
        List<Transaction> transactions = findByDateRangeByAccNumberDesc(accountnumber,fromdate, todate);

        ByteArrayInputStream in = ExcelHelper.transactionToExcel(transactions);
        return in;
      }
	
	
    @Override
    public String checkBalance(Integer id) {
    Details trans = drepo.findById(id).orElse(null);
        if (trans == null) {
            return "Transaction not found for ID: " + id;
        }
    Double balance = trans.getCurrentbalance();
    return "Current balance for Account ID " + id + " is : " + balance;
    }
    
    
    
    
    
    @Override
    public void saveFile(Integer id, String contentType, byte[] content) {
        FileEntity fileEntity = new FileEntity();
        Details dtls = new Details();
        fileEntity.setAccountnumber(dtls.getAccountnumber());
        fileEntity.setContentType(contentType);
        fileEntity.setContent(content);
        entityrepo.save(fileEntity);
    }

    public FileEntity getFileById(Long id) throws FileNotFoundException {
        return entityrepo.findById(id)
                .orElseThrow(() -> new FileNotFoundException("File not found: " + id));
    }
    
    public void updateFile(Integer accountnumber, String contentType, byte[] content) {
    	Details dtls = drepo.findByAccountnumber(accountnumber);
      if (dtls != null) {
          FileEntity fileEntity = new FileEntity();
          dtls.setAdharcard(content);
          fileEntity.setAccountnumber(dtls.getAccountnumber());
          fileEntity.setContentType(contentType);
          fileEntity.setContent(content);
          entityrepo.save(fileEntity);
          dtls.setFileentity(fileEntity);
          drepo.save(dtls);
      }
  }

	
	

	@Override
	public List<Transaction> findTopAllByOrderByDateDesc(Integer accountnumber) {
		return trepo.findTopAllByOrderByDateDesc(accountnumber);
		
	}

	@Override
	public Optional<Transaction> get1Transaction(Integer accountnumber) {
		
		return trepo.findById(accountnumber);
	}

	@Override
	public String getEmailByAccountNumber(Integer accountnumber) {
		return drepo.getEmailByAccountNumber(accountnumber);
	}

	@Override
	public List<Transaction> getLatestMonthTransactions(Integer accountnumber, LocalDate startDate, LocalDate endDate) {
		LocalDateTime startDateTime = startDate.atStartOfDay();
        LocalDateTime endDateTime = endDate.plusDays(1).atStartOfDay().minusSeconds(1);
        return trepo.findByDateRangeByAccNumberDesc(accountnumber,startDateTime, endDateTime);
	}
	public ByteArrayOutputStream generateTransactionsPdf(List<Transaction> transactions, Integer accountnumber) throws DocumentException {

		
		Details detls = drepo.findByAccountnumber(accountnumber);
		   Document document = new Document();
	        ByteArrayOutputStream baos = new ByteArrayOutputStream();

	        // Creating the PDF writer
	         PdfWriter.getInstance(document, baos);
	       

	        // Opening the document to write content
	        document.open();
	        
	        // Header part
	        PdfPTable table = new PdfPTable(2);
	        table.setWidths(new int[]{50, 50});
	        table.setWidthPercentage(100f);
//	        table.getDefaultCell().setBackgroundColor(Color.gray);
	        
	     // first cell
	        PdfPTable table1 = new PdfPTable(1);
	        table1.getDefaultCell().setMinimumHeight(30);
//	        table1.getDefaultCell().setBackgroundColor(Color.BLUE);

	        
	        table1.addCell("Account Number : " + detls.getAccountnumber());
	        table1.addCell("FullName : " + detls.getFullname());
	        table1.addCell("EmailId : " + detls.getEmail());
	        
	        table.addCell(new PdfPCell(table1));
	        
	        
	        // second cell
	        PdfPTable table2 = new PdfPTable(1);
	        table2.getDefaultCell().setMinimumHeight(30);
	    
	        table2.addCell("Account Type : " + detls.getAccounttype());
	        table2.addCell("Address : " + detls.getAddress());
	        table2.addCell("Avl Balance : " + detls.getCurrentbalance());
//	        PdfPCell cell = new PdfPCell(new Phrase("References"));
//	        cell.setMinimumHeight(40);
//	        cell.setColspan(2);
//	        table2.addCell(cell);
//	        cell = new PdfPCell(new Phrase("destination"));
//	        cell.setColspan(2);
//	        table2.addCell(cell);
	        table.addCell(new PdfPCell(table2));
	       
	     // second row
//	        cell = new PdfPCell();
//	        cell.setColspan(2);
//	        cell.setMinimumHeight(16);
//	        table.addCell(cell);
	        document.add(table);

	        // Creating the table
	        table = new PdfPTable(8);
	        table.setWidths(new int[]{ 5,5,5,5,5,5,5,5 });
	        table.setWidthPercentage(100f);
	        table.setSpacingBefore(5);

	        // Adding the table headers
	        addTableHeader(table);
	        

	        // Adding the table rows with transactions data
	        addTableRows(table, transactions);

	        // Adding the table to the document
	        document.add(table);

	        // Closing the document
	        document.close();

	        return baos;
	    }

	    private void addTableHeader(PdfPTable table) {
	        table.addCell("Transaction_Number");
	        table.addCell("AccountNumber");
	        table.addCell("Date");
	        table.addCell("FullName");
	        table.addCell("CurrentBalance");
	        table.addCell("Credit");
	        table.addCell("Debit");
	        table.addCell("Address");
	    }
	    
	    private String formatDateToString(LocalDateTime dateTime) {
			 DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
			return dateTime.format(myFormatObj).toString();
	    }

	    private void addTableRows(PdfPTable table, List<Transaction> transactions) {
	        for (Transaction transaction : transactions) {
	            table.addCell(transaction.getTransaction_number().toString());
	            table.addCell(transaction.getAccountnumber().toString());
	            table.addCell(formatDateToString(transaction.getDate()));
	            table.addCell(transaction.getFullname());
	            table.addCell(transaction.getCurrentbalance().toString());
	            table.addCell(transaction.getCredit().toString());
	            table.addCell(transaction.getDebit().toString());
	            table.addCell(transaction.getAddress());
	        }
	        
	        
	    }
	    

	public void uploadExcelData(MultipartFile file) throws IOException {
		  try (Workbook workbook = new XSSFWorkbook(file.getInputStream())) {
	            Sheet sheet = workbook.getSheetAt(0);
	            Iterator<Row> rowIterator = sheet.iterator();
	            List<Details> details = new ArrayList<>();
	            
	            if (rowIterator.hasNext()) {
	                rowIterator.next();
	            }
	            while (rowIterator.hasNext()) {
	            	Details detls = new Details();
	                Row row = rowIterator.next();
	                if (row.getRowNum() == 0) {
	                    // Skip header row
	                    continue;
	                }

	                if (row.getCell(0) != null) {
	                detls.setAccountnumber((int) row.getCell(0).getNumericCellValue());
	                }
	                if (row.getCell(1) != null) {
	                detls.setFirstname(row.getCell(1).getStringCellValue());
	                }
	                if (row.getCell(2) != null) {
	                detls.setLastname(row.getCell(2).getStringCellValue());
	                }
	                if (row.getCell(3) != null) {
	                detls.setFullname(row.getCell(3).getStringCellValue());
	                }
	                if (row.getCell(4) != null) {
	                detls.setEmail(row.getCell(4).getStringCellValue());
	                }
	                if (row.getCell(5) != null) {
	                detls.setPassword(row.getCell(5).getStringCellValue());
	                }
	                if (row.getCell(6) != null) {
	                detls.setAccounttype(row.getCell(6).getStringCellValue());
	                }
	                if (row.getCell(7) != null) {
	                detls.setAddress(row.getCell(7).getStringCellValue());
	                }
	                if (row.getCell(8) != null) {
	                detls.setGender(row.getCell(8).getStringCellValue());
	                }
	                if (row.getCell(9) != null) {
	                detls.setBranch(row.getCell(9).getStringCellValue());
	                } 
	                if (row.getCell(10) != null) {
	                detls.setIfsccode(row.getCell(10).getStringCellValue());
	                }
	                if (row.getCell(11) != null) {
	                detls.setMobilenumber((long) row.getCell(11).getNumericCellValue());
	                }
	                if (row.getCell(12) != null) {
	                detls.setPhoto(row.getCell(12).getStringCellValue());
	                }
	                if (row.getCell(13) != null) {
	                detls.setAdharcard(row.getCell(13).getStringCellValue());
	                }
	                if (row.getCell(14) != null) {
	                detls.setCurrentbalance(row.getCell(14).getNumericCellValue());
	                }
	                if (row.getCell(15) != null) {
	                detls.setStatus(row.getCell(15).getStringCellValue());
	                }
	                details.add(detls);
	                
	                Transaction t=new Transaction();
	        		t.setAccountnumber(detls.getAccountnumber());
	        		t.setAddress(detls.getAddress());
	        		t.setCredit(detls.getCurrentbalance());
	        		t.setDebit(0.0);
	        		t.setCurrentbalance(detls.getCurrentbalance());
	        		t.setFullname(detls.getFirstname()+detls.getLastname());
	        		LocalDateTime date = LocalDateTime.now();
	        		t.setDate(date);
	        		trepo.save(t);
	                
	                
	            }
	            drepo.saveAll(details);
		  }
	}

	
	

	

}
