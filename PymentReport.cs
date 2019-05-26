using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Windows.Forms;

namespace TollBooth
{
    public partial class PymentReport : Form
    {
        public PymentReport()
        {
            InitializeComponent();
        }

        private void PymentReport_Load(object sender, EventArgs e)
        {

        }

        private void btnsearch_Click(object sender, EventArgs e)
        {
            string error = "", qry = "";
            qry = "SELECT sum(PaymentAmt) as PaymentAmt FROM dbo.tollpayments where TollId='" + GeneralFunctions.tollid + "' and PayDate='" + DateTime.Now.ToString("yyyy/MM/dd") + "'";
            qry = "SELECT PayDate as PaymentDate,   SUM(PaymentAmt) AS PaymentAmount  FROM tollpayments WHERE (TollId = '" + GeneralFunctions.tollid + "') AND (PayDate BETWEEN '" + Convert.ToDateTime(dtp1.Text).ToString("yyyy/MM/dd") + "' AND '" + Convert.ToDateTime(dtp2.Text).ToString("yyyy/MM/dd") + "') GROUP BY PayDate";
            DataTable dt = GeneralFunctions.ExecuteDataTable(qry, out error);
            dgv1.DataSource = dt;
        }
    }
}
