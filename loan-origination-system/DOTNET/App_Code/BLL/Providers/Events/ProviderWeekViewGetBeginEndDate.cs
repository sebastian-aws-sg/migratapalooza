#region XD World Recipe V 3
// FileName: ProviderWeekViewGetBeginEndDate.cs
// Author: Dexter Zafra
// Date Created: 4/20/2009
// Website: www.ex-designz.net
#endregion
using System;
using System.Data;
using XDRecipe.BL;
using XDRecipe.Common;
using XDRecipe.Model;

namespace XDRecipe.BL.Providers.Events
{
    /// <summary>
    /// object in this class returns week view begin and end date
    /// </summary>
    public class ProviderWeekViewGetBeginEndDate : EventsCalendar
    {
        public ProviderWeekViewGetBeginEndDate()
        {
        }

        public void FillUp(int Year, int NumWeek)
        {
            IDataReader dr = Blogic.ActionProcedureDataProvider.EventWeekViewBeginAndEndDate(Year, NumWeek);

            while (dr.Read())
            {
                if (dr["WeekBegindate"] != DBNull.Value)
                {
                    this._WeekBegindate = (DateTime)(dr["WeekBegindate"]);
                }
                if (dr["WeekEnddate"] != DBNull.Value)
                {
                    this._WeekEnddate = (DateTime)(dr["WeekEnddate"]);
                }
            }

            dr.Close();
            dr = null;
        }
    }
}
