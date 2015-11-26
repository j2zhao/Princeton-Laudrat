//
//  ViewController.swift
//  Laundry View
//
//  Created by Rachana Balasubramanian on 11/14/15.
//  Copyright © 2015 Rachana Balasubramanian. All rights reserved.
//

import UIKit
import Parse
import Bolts

class ViewController: UIViewController, UITableViewDataSource, UITableViewDelegate {
    
    let ScreenSize = UIScreen.mainScreen().bounds
    var ScreenWidth:CGFloat?
    var ScreenHeight:CGFloat?
    let DEFAULT_FONT:String = "Consolas"
    let MainView = UIView()
    //Laundry List
    var CompleteLaundry = UITableView()
    var LaundryNames = [String](count: 47, repeatedValue: "0.0")
    let ObjectID:[String] = ["iXtVP8KwKd","ny1ByMkJbB", "6WihvXD9Sw", "l0IYvqIhs6", "CkoUS3BFZN", "bryNVDyA1K", "7STA7CpE0n", "rLJXfw1k9j", "3Qc2taqP3g", "bLzl07Ih1R", "PaSPj7sAo9", "DhE5UCgm18","IMW6MQOhIh", "mhpdd6wunZ", "cwTv4mrzCd", "3ydoPBHxVy", "4YGnW7JdBC", "w5DapjgGLU", "cv8Mf1d1eN", "OsOnW8mQX7", "0JqEMn5Rmf", "LmzDNKviBB", "uDou77uhTu", "XMFlkIoRzg", "iDCMpmWoLx", "V2mRsD2p5I", "Hob5NRMcE4", "uHGbyP4DOq", "3F2nDWT9js", "Zw56CdBfHr", "gWb60EK6xl", "k8kYSbk33K", "ng000ewqgv", "TQxVtfZfIU", "2P73zWKbpi", "N6KG6mIaUV", "scHoSdDpnw", "1zN4NvnlZC", "FCfl4rO78S", "9mAvWfm7tX", "2PnI3VuKyu", "E8odpnlz38", "3XU7HqrWWb", "nE51toA8U2", "nFCHnmyqJ1", "Iv03V6WlOH"]
    
    func setTable(info: [String]){
        CompleteLaundry = UITableView()
        LaundryNames = info
        //LaundryNames = laundryInfo
        //Table stuff
        ScreenHeight = ScreenSize.height
        ScreenWidth = ScreenSize.width
        self.CompleteLaundry.registerClass(UITableViewCell.self, forCellReuseIdentifier: "cell")
        self.CompleteLaundry.dataSource = self
        self.CompleteLaundry.delegate = self
        self.CompleteLaundry.allowsMultipleSelectionDuringEditing = false
        
        //table stuff
        //CompleteLaundry.backgroundColor = UIColor.blueColor()
        CompleteLaundry.frame = CGRectMake(0.05*ScreenWidth!, 0.1*ScreenHeight!, 0.9*ScreenWidth!, 0.8*ScreenHeight!)
        CompleteLaundry.reloadData()
        MainView.addSubview(CompleteLaundry)
        
    }
    
    func delay(delay: Double, closure: ()->()) {
        dispatch_after(
            dispatch_time(
                DISPATCH_TIME_NOW,
                Int64(delay * Double(NSEC_PER_SEC))
            ),
            dispatch_get_main_queue(),
            closure
        )
    }
    
    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    func tableView(tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return LaundryNames.count
    }
    
    func tableView(tableView: UITableView, commitEditingStyle editingStyle: UITableViewCellEditingStyle, forRowAtIndexPath indexPath: NSIndexPath) {
    }
    
    func tableView(tableView: UITableView, cellForRowAtIndexPath indexPath: NSIndexPath) -> UITableViewCell {
        var cell:UITableViewCell = self.CompleteLaundry.dequeueReusableCellWithIdentifier("cell")!
        if(cell.isEqual(nil)){
            cell = UITableViewCell(style: UITableViewCellStyle.Default, reuseIdentifier: "cell")
        }
        cell.textLabel?.text = LaundryNames[indexPath.row]
        cell.textLabel?.font = UIFont(name: DEFAULT_FONT, size: 15)
        cell.backgroundColor = UIColor(white: 1.0, alpha: 0)
        cell.selectionStyle = UITableViewCellSelectionStyle.None
        return cell
    }
    
    func tableView(tableView: UITableView, editingStyleForRowAtIndexPath indexPath: NSIndexPath) -> UITableViewCellEditingStyle {
        return UITableViewCellEditingStyle.None;
    }
    
    
    override func viewDidLoad() {
        super.viewDidLoad()
        // Do any additional setup after loading the view, typically from a nib.
        ScreenHeight = ScreenSize.height
        ScreenWidth = ScreenSize.width
        MainView.frame = CGRectMake(0, 0, ScreenWidth!, ScreenHeight!)
        self.view.addSubview(MainView)
        
        //let LaundryPeriodsView = UIView()
        //LaundryPeriodsView.frame = CGRectMake(0, 0, ScreenWidth!, ScreenHeight!)
        
        //let EditPeriodView = UIView()
        //EditPeriodView.frame = CGRectMake(0, 0, ScreenWidth!, ScreenHeight!)
        
        CompleteLaundry = UITableView()
        var laundryRoom: PFObject
        var name: String
        var washer: String
        var dryer: String
        LaundryNames[0] = "Room/Washing Machine/Dryer"
        for i in 1...46 {
            let query = PFQuery(className:"LaundryRoom")
                laundryRoom = try! query.getObjectWithId(self.ObjectID[i-1])
                name = laundryRoom["Name"] as! String
                
                washer = laundryRoom["Washer"] as! String
                dryer = laundryRoom["Dryer"] as! String
                
                LaundryNames[i] = name + " / " + washer + " / " + dryer
            
        }
        //LaundryNames = laundryInfo
        //Table stuff
        CompleteLaundry.registerClass(UITableViewCell.self, forCellReuseIdentifier: "cell")
        CompleteLaundry.dataSource = self
        CompleteLaundry.delegate = self
        CompleteLaundry.allowsMultipleSelectionDuringEditing = false
        
        //table stuff
        //CompleteLaundry.backgroundColor = UIColor.blueColor()
        CompleteLaundry.frame = CGRectMake(0.05*ScreenWidth!, 0.1*ScreenHeight!, 0.9*ScreenWidth!, 0.8*ScreenHeight!)
        CompleteLaundry.reloadData()
        MainView.addSubview(CompleteLaundry)
        

    }
    
}
